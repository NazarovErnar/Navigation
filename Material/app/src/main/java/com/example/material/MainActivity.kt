package com.example.material

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.material.ui.theme.MaterialTheme

private val Any.body1: body1
    get() = body1

class body1 {

}

private val Any.h1: Unit
    get() = Unit
private val CustomColors = lightColors(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFFF2F2F2),
    surface = Color.White,
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

fun lightColors(
    primary: Color,
    secondary: Color,
    background: Color,
    surface: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    onError: Color
) {

}

private val CustomTypography = Typography(
    h1 = TextStyle(fontSize = 30.sp),
    body1 = TextStyle(fontSize = 16.sp)
)

class TextStyle(fontSize: TextUnit) {

}

class Typography(h1: Any, body1: Any) {

}

private val CustomShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
fun CustomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = CustomColors,
        typography = CustomTypography,
        shapes = CustomShapes,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Заголовок", style = MaterialTheme().javaClass.h1)
        Text(
            text = "Основной текст",
            style = MaterialTheme().javaClass.body1
        )
        Button(onClick = {}) {
            Text("Кнопка")
        }
    }
}

fun Text(text: String, style: Unit) {
    TODO("Not yet implemented")
}

fun Text(text: String, style: body1) {
    TODO("Not yet implemented")
}

fun Text(text: String, style: TextStyle) {

}

fun MaterialTheme() {
    TODO("Not yet implemented")
}

@Composable
fun Button(onClick: () -> Unit, content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    CustomTheme {
        AppContent()
    }
}