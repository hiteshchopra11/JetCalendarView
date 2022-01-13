package dev.baseio.libjetcalendar.data

import java.util.*

data class JetWeek(
  val startDate: Date,
  val endDate: Date,
  val focusedDateOfMonth: Date,
) {
  companion object {
    fun current(date: Date = Date(), focusedDate: Date): JetWeek {
      return Calendar.getInstance().run {
        time = date
        set(Calendar.DAY_OF_WEEK, 1)
        val startDate = this.time
        set(Calendar.DAY_OF_WEEK, 7)
        val endDate = this.time
        JetWeek(startDate, endDate, focusedDate)
      }
    }
  }
}

fun JetWeek.dates(): List<JetDay> {
  val weekDates = mutableListOf<JetDay>()
  weekDates.add(startDate.toJetDay(this))
  while (weekDates.size != 7) {
    weekDates.add(weekDates.last().nextDay(this))
  }
  return weekDates
}

fun Date.toJetDay(jetWeek: JetWeek): JetDay {
  return Calendar.getInstance().run {
    time = this@toJetDay
    val year: Int = get(Calendar.YEAR)
    val month: Int = get(Calendar.MONTH) + 1
    val day: Int = get(Calendar.DAY_OF_MONTH)
    JetDay(
      year,
      month,
      day,
      get(Calendar.MONTH) == jetWeek.focusedDateOfMonth.getDateMonth()
    )
  }
}

private fun Date.getDateMonth(): Int {
  return Calendar.getInstance().run {
    time = this@getDateMonth
    get(Calendar.MONTH)
  }
}

private fun JetDay.nextDay(jetWeek: JetWeek): JetDay {
  return Calendar.getInstance().run {
    set(Calendar.YEAR, this@nextDay.year)
    set(Calendar.MONTH, this@nextDay.month - 1)
    set(Calendar.DAY_OF_MONTH, this@nextDay.day)
    add(Calendar.DAY_OF_YEAR, 1)
    this.time.toJetDay(jetWeek)
  }
}

fun JetWeek.nextWeek(): JetWeek {
  return Calendar.getInstance().run {
    time = this@nextWeek.endDate
    add(Calendar.DAY_OF_YEAR, 1)
    val startDateNew = this.time
    add(Calendar.DAY_OF_YEAR, 6)
    val endDateNew = this.time
    JetWeek(startDateNew, endDateNew, focusedDateOfMonth)
  }
}

fun JetWeek.previousWeek(): JetWeek {
  return Calendar.getInstance().run {
    time = this@previousWeek.startDate
    add(Calendar.DAY_OF_YEAR, -1)
    val endDateNew = this.time
    add(Calendar.DAY_OF_YEAR, -6)
    val startDateNew = this.time
    JetWeek(startDateNew, endDateNew, focusedDateOfMonth)
  }
}