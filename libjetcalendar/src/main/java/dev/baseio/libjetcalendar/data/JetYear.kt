package dev.baseio.libjetcalendar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters


@Parcelize
class JetYear private constructor(
  val startDate: LocalDate,
  val endDate: LocalDate,
  var yearMonths: List<JetMonth>? = null
) : Parcelable, JetCalendarType() {

  fun currentMonthPosition(): Int {
    return YearMonth.now().monthValue - 1
  }

  companion object {
    fun current(date: LocalDate = LocalDate.now(), firstDayOfWeek: DayOfWeek): JetYear {
      val day: LocalDate = date.with(TemporalAdjusters.firstDayOfYear())
      val last: LocalDate = date.with(TemporalAdjusters.lastDayOfYear())
      val year = JetYear(day, last)
      year.yearMonths = year.months(firstDayOfWeek)
      return year
    }
  }

  private fun months(firstDayOfWeek: DayOfWeek): List<JetMonth> {
    val months = mutableListOf<JetMonth>()

    var startDateMonth = this.startDate.withDayOfMonth(1)
    var endDateMonth = this.startDate.withDayOfMonth(this.startDate.lengthOfMonth())

    var currentYear = this.startDate.year
    while (true) {
      months.add(JetMonth.current(startDateMonth, firstDayOfWeek))

      startDateMonth = endDateMonth.plusDays(1)
      endDateMonth = startDateMonth.withDayOfMonth(startDateMonth.lengthOfMonth())
      if (endDateMonth.year > currentYear) {
        break
      }
      currentYear = endDateMonth.year
    }
    return months
  }
}
