package dev.baseio.libjetcalendar.week

import java.util.*

data class JetWeek(val startDate: Date, val endDate: Date) {

  companion object {
    fun current(): JetWeek {
      return Calendar.getInstance().run {
        set(Calendar.DAY_OF_WEEK, 1)
        val startDate = this.time
        set(Calendar.DAY_OF_WEEK, 7)
        val endDate = this.time
        JetWeek(startDate, endDate)
      }
    }
  }
}

fun JetWeek.dates(): List<JetDay> {
  val weekDates = mutableListOf<JetDay>()
  weekDates.add(startDate.toJetWeekDay())
  while (weekDates.size != 7) {
    weekDates.add(weekDates.last().addDays(1))
  }
  return weekDates
}

fun Date.toJetWeekDay(): JetDay {
  return Calendar.getInstance().run {
    val year: Int = get(Calendar.YEAR)
    val month: Int = get(Calendar.MONTH) + 1
    val day: Int = get(Calendar.DAY_OF_MONTH)
    JetDay(year, month, day)
  }
}

private fun JetDay.addDays(days: Int): JetDay {
  return Calendar.getInstance().run {
    set(Calendar.YEAR, this@addDays.year)
    set(Calendar.MONTH, this@addDays.month - 1)
    set(Calendar.DAY_OF_MONTH, this@addDays.day)
    add(Calendar.DAY_OF_YEAR, days)
    this.time.toJetWeekDay()
  }
}

fun JetWeek.nextWeek(): JetWeek {
  return Calendar.getInstance().run {
    time = this@nextWeek.endDate
    add(Calendar.DAY_OF_YEAR, 1)
    val startDateNew = this.time
    add(Calendar.DAY_OF_YEAR, 6)
    val endDateNew = this.time
    JetWeek(startDateNew, endDateNew)
  }
}

fun JetWeek.previousWeek(): JetWeek {
  return Calendar.getInstance().run {
    time = this@previousWeek.startDate
    add(Calendar.DAY_OF_YEAR, -1)
    val endDateNew = this.time
    add(Calendar.DAY_OF_YEAR, -6)
    val startDateNew = this.time
    JetWeek(startDateNew, endDateNew)
  }
}