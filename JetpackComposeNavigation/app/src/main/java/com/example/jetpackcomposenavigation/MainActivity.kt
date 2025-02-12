package com.example.jetpackcomposenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                NavigationApp()
            }
        }
    }
}

class MyApplicationTheme(function: @Composable () -> Unit) {

}

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}

annotation class GET(val value: String)

data class User(
    val id: Int,
    val name: String,
    val username: String
)

class GsonConverterFactory {
    companion object {
        fun create() {

        }
    }

}

private fun <A, B> Pair<A, B>.to(): B {
    TODO("Not yet implemented")
}

class UserViewModel : ViewModel() {
    private val retrofit = ((Retrofit.Builder() to "https://jsonplaceholder.typicode.com/") to GsonConverterFactory.create())
        .to()

    private val apiService = retrofit.to(ApiService::class.java)

    val users = mutableStateOf<List<User>>(emptyList())


    }

    @Composable
    private fun fetchUsers() {
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                val apiService = null
                val result = apiService.get()
                val users = null
                users.dp = result
            }
        }
    }

private fun Any.withContext(context: CoroutineDispatcher, block: suspend CoroutineScope.() -> Unit) {

}

fun withContext(context: CoroutineDispatcher, block: suspend CoroutineScope.() -> Unit) {
    TODO("Not yet implemented")
}

private fun Nothing?.get() {

}

class Retrofit {
    class Builder {

    }

}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "userList") {
        composable("userList") { UserListScreen(navController) }
        composable("userDetail/{userName}") { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            UserDetailScreen(userName)
        }
    }
}

@Composable
fun UserListScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val users = viewModel.users.value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(users.size) { index ->
            val user = users[index]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { navController.navigate("userDetail/${user.name}") }
            ) {
                Text(text = user.name, color = Color.Black, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UserDetailScreen(userName: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + scaleIn(),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = rememberImagePainter("https://via.placeholder.com/150"),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = userName, style = MaterialTheme.typography.headlineLarge)
            }
        }
    }
}

fun rememberImagePainter(s: String): Painter {

}

@Composable
fun LoadImage(url: String) {
    val painter = rememberImagePainter(url)
    Image(painter = painter, contentDescription = "Loaded Image")
}
