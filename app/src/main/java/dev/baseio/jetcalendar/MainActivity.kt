package dev.baseio.jetcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.baseio.jetcalendar.ui.theme.JetCalendarSampleTheme
import dev.baseio.libjetcalendar.JetCalendar
import dev.baseio.libjetcalendar.data.JetDay
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      JetCalendarSampleTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
          CalendarExample()
        }
      }
    }
  }
}

@Composable
fun CalendarExample() {
  var stateFlow by rememberSaveable { mutableStateOf(setOf<JetDay>()) }
  JetCalendar(modifier = Modifier, onDateSelected = { jetDay ->
    stateFlow = hashSetOf<JetDay>().apply {
      addAll(stateFlow)
      add(jetDay)
    }
  }, selectedDates = stateFlow)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  JetCalendarSampleTheme {
    CalendarExample()
  }
}