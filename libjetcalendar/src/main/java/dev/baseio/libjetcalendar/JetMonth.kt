package dev.baseio.libjetcalendar

import java.text.DateFormatSymbols
import java.util.*


class JetMonth(val startDate: Date, val endDate: Date) {
  fun name(): String {
    return Calendar.getInstance().run {
      time = startDate
      DateFormatSymbols().months[get(Calendar.MONTH)]
    }
  }

  companion object {
    fun current(date: Date = Date()): JetMonth {
      return Calendar.getInstance().run {
        time = date
        this[Calendar.DAY_OF_MONTH] = getActualMinimum(Calendar.DAY_OF_MONTH)
        this[Calendar.HOUR_OF_DAY] = 0
        this[Calendar.MINUTE] = 0
        this[Calendar.SECOND] = 0
        val startDate = this.time

        this.time = date
        this[Calendar.DAY_OF_MONTH] = this.getActualMaximum(Calendar.DAY_OF_MONTH)
        this[Calendar.HOUR_OF_DAY] = 23
        this[Calendar.MINUTE] = 59
        this[Calendar.SECOND] = 59
        val endDate = this.time

        JetMonth(startDate, endDate)
      }
    }
  }
}

fun JetMonth.weeks(): List<JetWeek> {
  val weeks = Calendar.getInstance().run {
    time = this@weeks.startDate
    getActualMaximum(Calendar.WEEK_OF_MONTH)
  }
  val monthWeeks = mutableListOf<JetWeek>()
  monthWeeks.add(JetWeek.current(this@weeks.startDate))
  while (monthWeeks.size != weeks) {
    monthWeeks.add(monthWeeks.last().nextWeek())
  }
  return monthWeeks
}

fun JetMonth.dates(): List<JetDay> {
  val days = Calendar.getInstance().run {
    time = this@dates.startDate
    getActualMaximum(Calendar.DAY_OF_MONTH)
  }

  val monthDates = mutableListOf<JetDay>()
  monthDates.add(startDate.toJetMonthDay())
  while (monthDates.size != days) {
    monthDates.add(monthDates.last().addDays(1))
  }
  return monthDates
}

fun Date.toJetMonthDay(): JetDay {
  return Calendar.getInstance().run {
    time = this@toJetMonthDay
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
    this.time.toJetMonthDay()
  }
}

fun JetMonth.nextMonth(): JetMonth {
  val currentMonth = this
  return Calendar.getInstance().run {
    time = currentMonth.endDate
    add(Calendar.DAY_OF_YEAR, 1)
    val startDateNew = this.time
    val days = getActualMaximum(Calendar.DAY_OF_MONTH)
    set(Calendar.DAY_OF_MONTH, days)
    val endDateNew = this.time
    JetMonth(startDateNew, endDateNew)
  }
}