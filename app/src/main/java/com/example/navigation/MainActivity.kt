package com.example.navigation

        import android.Manifest
                import android.annotation.SuppressLint
                import android.content.Context
                import android.location.Location
                import android.os.Bundle
                import android.widget.Toast
                import androidx.activity.ComponentActivity
                import androidx.activity.compose.setContent
                import androidx.activity.result.contract.ActivityResultContracts
                import androidx.compose.foundation.clickable
                import androidx.compose.foundation.layout.*
                import androidx.compose.material3.*
                import androidx.compose.runtime.*
                import androidx.compose.ui.Modifier
                import androidx.compose.ui.unit.dp
                import androidx.lifecycle.ViewModel
                import androidx.lifecycle.ViewModelProvider
                import androidx.lifecycle.viewModelScope
                import androidx.navigation.NavController
                import androidx.navigation.NavHostController
                import androidx.navigation.compose.NavHost
                import androidx.navigation.compose.composable
                import androidx.navigation.compose.rememberNavController
                import com.google.android.gms.location.*
                import kotlinx.coroutines.Dispatchers
                import kotlinx.coroutines.launch
                import kotlinx.coroutines.withContext
                import androidx.room.*

                @Entity
                data class Place(
                    @PrimaryKey(autoGenerate = true) val id: Int = 0,
                    val latitude: Double,
                    val longitude: Double
                )

        @Dao
        interface PlaceDao {
            @Insert
            suspend fun insertPlace(place: Place)

            @Query("SELECT * FROM Place")
            suspend fun getAllPlaces(): List<Place>

            @Query("SELECT * FROM Place ORDER BY id DESC LIMIT 1")
            suspend fun getLastPlace(): Place?
        }

        @Database(entities = [Place::class], version = 1)
        abstract class AppDatabase : RoomDatabase() {
            abstract fun placeDao(): PlaceDao
        }

        class PlaceViewModel(context: Context) : ViewModel() {
            private val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "places-database"
            ).build()

            val places = mutableStateListOf<Place>()

            init {
                viewModelScope.launch {
                    val savedPlaces = db.placeDao().getAllPlaces()
                    places.addAll(savedPlaces)
                }
            }

            suspend fun addPlace(latitude: Double, longitude: Double) {
                val newPlace = Place(latitude = latitude, longitude = longitude)
                db.placeDao().insertPlace(newPlace)
                withContext(Dispatchers.Main) { places.add(newPlace) }
            }

            suspend fun getLastPlace(): Place? {
                return db.placeDao().getLastPlace()
            }
        }

        class MainActivity : ComponentActivity() {
            private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
            private val locationPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    Toast.makeText(this, "Геолокацияға рұқсат берілді", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Геолокацияға рұқсат жоқ", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                setContent {
                    val navController = rememberNavController()
                    val viewModel: PlaceViewModel = ViewModelProvider(this)[PlaceViewModel::class.java]
                    MyApp(navController, viewModel)
                }
                requestLocationPermission()
            }

            private fun requestLocationPermission() {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            @SuppressLint("MissingPermission")
            private fun requestLocation(context: Context, onSuccess: (Location) -> Unit) {
                val locationRequest = LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 10000
                }

                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location ->
                        if (location != null) onSuccess(location)
                        else Toast.makeText(context, "Не удалось получить местоположение", Toast.LENGTH_SHORT).show()
                    }
            }

            @OptIn(ExperimentalMaterial3Api::class)
            @Composable
            fun MyApp(navController: NavHostController, viewModel: PlaceViewModel) {
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Мои места") })
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "list_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("list_screen") { PlaceListScreen(viewModel, navController) }
                        composable("map_screen") { MapScreen(viewModel) }
                    }
                }
            }

            @Composable
            fun PlaceListScreen(viewModel: PlaceViewModel, navController: NavController) {
                Column(modifier = Modifier.padding(16.dp)) {
            Button(onClick = {
                requestLocation(this@MainActivity) { location ->
                    viewModel.viewModelScope.launch {
                        viewModel.addPlace(location.latitude, location.longitude)
                    }
                }
            }) {
                Text("Добавить текущее место")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Сохраненные места:")
            for (place in viewModel.places) {
                Text("${place.latitude}, ${place.longitude}",
                    modifier = Modifier.clickable { navController.navigate("map_screen") })
            }
        }
    }

    @Composable
    fun MapScreen(viewModel: PlaceViewModel) {
        val place = viewModel.places.lastOrNull()

        place?.let {
            Text("Место: ${it.latitude}, ${it.longitude}")
        } ?: Text("Нет сохранённых мест для отображения")
    }
}
