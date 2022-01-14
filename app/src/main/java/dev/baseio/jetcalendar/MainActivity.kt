package dev.baseio.jetcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.jetcalendar.ui.theme.JetCalendarSampleTheme
import dev.baseio.libjetcalendar.JetCalendar
import dev.baseio.libjetcalendar.data.JetDay
import dev.baseio.libjetcalendar.data.JetViewType

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
  var viewTypeFlow by remember {
    mutableStateOf(JetViewType.MONTHLY)
  }
  Surface(color = MaterialTheme.colors.background) {
    Scaffold(topBar = {
      TopAppBar(title = {
        Column(){
          Text("Calendar Example")
          Text(viewTypeFlow.toString(), style = TextStyle(fontSize = 12.sp))
        }
      }, actions = {
        IconButton(modifier = Modifier.then(Modifier.padding(8.dp)),
          onClick = {
            stateFlow = hashSetOf()
          }, content = {
            Icon(
              Icons.Filled.Clear,
              "clear selection",
              tint = Color.White
            )
          })
        IconButton(modifier = Modifier.then(Modifier.padding(8.dp)),
          onClick = {
            viewTypeFlow = viewTypeFlow.next()
          }, content = {
            Icon(
              Icons.Filled.List,
              "switch view type",
              tint = Color.White
            )
          })
      })
    }) {
      JetCalendar(modifier = Modifier, viewType = viewTypeFlow, onDateSelected = { jetDay ->
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