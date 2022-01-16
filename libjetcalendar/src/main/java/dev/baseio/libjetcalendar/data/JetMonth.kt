package dev.baseio.libjetcalendar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*


@Parcelize
class JetMonth private constructor(
  val startDate: LocalDate,
  val endDate: LocalDate,
  var firstDayOfWeek: DayOfWeek,
  var monthWeeks: List<JetWeek>? = null
) :
  Parcelable, JetCalendarType() {
  fun name(): String {
    return "${
      startDate.month.getDisplayName(
        TextStyle.SHORT,
        Locale.getDefault()
      )
    } ${startDate.year}"
  }


  companion object {
    fun current(date: LocalDate = LocalDate.now(), firstDayOfWeek: DayOfWeek): JetMonth {
      val startOfMonth = date.with(TemporalAdjusters.firstDayOfMonth())
      val endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth())
      val month =  JetMonth(startOfMonth, endOfMonth, firstDayOfWeek = firstDayOfWeek)
      month.monthWeeks = month.weeks(firstDayOfWeek)
      return month
    }
  }

  private fun weeks(firstDayOfWeek: DayOfWeek): List<JetWeek> {
    val currentYearMonth: YearMonth = YearMonth.of(this.endDate.year, this.endDate.monthValue)
    val weeks = currentYearMonth.atEndOfMonth().get(WeekFields.of(firstDayOfWeek, 1).weekOfMonth())
    val monthWeeks = mutableListOf<JetWeek>()
    monthWeeks.add(
      JetWeek.current(
        startDate,
        isFirstWeek = true,
        dayOfWeek = this.firstDayOfWeek
      )
    )
    while (monthWeeks.size != weeks) {
      monthWeeks.add(monthWeeks.last().nextWeek(isFirstWeek = false))
    }
    return monthWeeks
  }

}


