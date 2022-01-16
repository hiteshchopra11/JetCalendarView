package dev.baseio.jetcalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
  var selectionState = mutableSetOf<JetDay>()
  var viewTypeFlow by remember {
    mutableStateOf(JetViewType.YEARLY)
  }

  Surface(color = MaterialTheme.colors.background) {
    Scaffold(topBar = {
      TopAppBar(title = {
        Column {
          Text("JetCalendar")
          Text(viewTypeFlow.toString(), style = TextStyle(fontSize = 12.sp))
        }
      }, actions = {
        IconButton(modifier = Modifier.then(Modifier.padding(8.dp)),
          onClick = {
            selectionState = hashSetOf()
          }, content = {
            Icon(
              Icons.Filled.Clear,
              "clear selection",
              tint = Color.Black
            )
          })
        IconButton(modifier = Modifier.then(Modifier.padding(8.dp)),
          onClick = {
            viewTypeFlow = viewTypeFlow.next()
          }, content = {
            Icon(
              Icons.Filled.List,
              "switch view type",
              tint = Color.Black
            )
          })
      })
    }) {
      JetCalendar(modifier = Modifier, viewType = viewTypeFlow, onDateSelected = { jetDay ->
        selectionState = hashSetOf<JetDay>().apply {
          addAll(selectionState)
          add(jetDay)
        }
      }, selectedDates = selectionState)
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