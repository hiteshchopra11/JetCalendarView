package dev.baseio.jetcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.jetcalendar.ui.theme.JetCalendarSampleTheme
import dev.baseio.libjetcalendar.JetCalendar
import dev.baseio.libjetcalendar.data.JetDay

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      JetCalendarSampleTheme {
        // A surface container using the 'background' color from the theme
        CalendarExample()
      }
    }
  }
}

@Composable
fun CalendarExample() {
  var stateFlow by rememberSaveable { mutableStateOf(setOf<JetDay>()) }
  Surface(color = MaterialTheme.colors.background) {
    Scaffold(topBar = {
      TopAppBar(title = {
        Text("Calendar Example")
      }, actions = {
        IconButton(modifier = Modifier.then(Modifier.padding(8.dp)),
          onClick = {
            stateFlow = hashSetOf()
          }) {
          Icon(
            Icons.Filled.Clear,
            "clear selection",
            tint = Color.White
          )
        }
      })
    }) {
      JetCalendar(modifier = Modifier, onDateSelected = { jetDay ->
        stateFlow = hashSetOf<JetDay>().apply {
          addAll(stateFlow)
          add(jetDay)
        }
      }, selectedDates = stateFlow)
    }
  }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  JetCalendarSampleTheme {
    CalendarExample()
  }
}