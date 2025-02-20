package com.example.notificationcompat

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        setContent {
            NotificationApp()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "notification_channel",
                "Основной канал",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Канал для локальных уведомлений"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun NotificationApp() {
    val context = LocalContext.current
    var isNotifying by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            isNotifying = true
        }) {
            Text("Уведомить")
        }
    }

    if (isNotifying) {
        LaunchedEffect(Unit) {
            delay(10000)
            sendNotification(context)
            isNotifying = false
        }
    }
}

class LocalContext {

}

fun sendNotification(context: Context) {
    val builder = NotificationCompat.Builder(context, "notification_channel")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Уведомление")
        .setContentText("Это ваше уведомление!")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        notify(1, builder.build())
    }
}