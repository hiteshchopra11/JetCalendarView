package dev.baseio.libjetcalendar.data

import java.util.*

class JetYear(val startDate: Date, val endDate: Date) {
  fun currentMonth(): Int {
    return Calendar.getInstance().run {
      time = startDate
      get(Calendar.MONTH)
    }
  }

  companion object {
    fun current(date: Date = Date()): JetYear {
      return Calendar.getInstance().run {
        time = date
        set(Calendar.DAY_OF_YEAR, 1)
        val startDate = this.time
        set(Calendar.DAY_OF_YEAR, getActualMaximum(Calendar.DAY_OF_YEAR))
        val endDate = this.time
        JetYear(startDate, endDate)
      }
    }
  }
}

fun JetYear.months(): List<JetMonth> {
  val months = mutableListOf<JetMonth>()

  Calendar.getInstance().run {
    this[Calendar.DAY_OF_YEAR] = 1
    var startDateMonth = this.time
    val totalDaysInThisMonth = getActualMaximum(Calendar.DAY_OF_MONTH)
    this[Calendar.DAY_OF_MONTH] = totalDaysInThisMonth
    var endDateMonth = this.time
    var currentYear = get(Calendar.YEAR)
    while (true) {
      months.add(JetMonth(startDateMonth, endDateMonth))
      add(Calendar.DAY_OF_YEAR, 1)
      if (get(Calendar.YEAR) > currentYear) {
        break
      }
      startDateMonth = this.time
      val newDays = getActualMaximum(Calendar.DAY_OF_MONTH)
      this[Calendar.DAY_OF_MONTH] = newDays
      endDateMonth = this.time
      currentYear = get(Calendar.YEAR)
    }
  }
  return months
}
