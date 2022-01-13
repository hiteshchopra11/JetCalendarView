package dev.baseio.jetcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.baseio.jetcalendar.ui.theme.JetCalendarSampleTheme
import dev.baseio.libjetcalendar.JetCalendar
import dev.baseio.libjetcalendar.monthly.JetCalendarMonthlyView
import dev.baseio.libjetcalendar.weekly.JetCalendarWeekView

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      JetCalendarSampleTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          Greeting("Android")
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String) {
  JetCalendar(modifier = Modifier)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  JetCalendarSampleTheme {
    Greeting("Android")
  }
}