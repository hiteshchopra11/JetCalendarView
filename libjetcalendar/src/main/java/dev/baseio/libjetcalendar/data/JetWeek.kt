package dev.baseio.libjetcalendar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

@Parcelize
data class JetWeek(
  val startDate: LocalDate,
  val endDate: LocalDate,
  val monthOfWeek: Int,
  val dayOfWeek: DayOfWeek,
  val isFirstWeek: Boolean,
) : Parcelable, JetCalendarType() {
  fun dayNames(): List<String> {
    val days = mutableListOf<DayOfWeek>()
    days.add(dayOfWeek)
    while (days.size != 7) {
      days.add(days.last().plus(1))
    }
    return days.map { it.getDisplayName(java.time.format.TextStyle.SHORT, Locale.getDefault()) }
  }

  companion object {
    fun current(
      date: LocalDate = LocalDate.now(),
      dayOfWeek: DayOfWeek,
      isFirstWeek: Boolean
    ): JetWeek {
      val startOfCurrentWeek: LocalDate =
        date.with(TemporalAdjusters.previousOrSame(dayOfWeek))
      val lastDayOfWeek = dayOfWeek.plus(6) // or minus(1)
      val endOfWeek: LocalDate = date.with(TemporalAdjusters.nextOrSame(lastDayOfWeek))
      return JetWeek(startOfCurrentWeek, endOfWeek, date.monthValue, dayOfWeek, isFirstWeek)
    }
  }
}

fun JetWeek.dates(): List<JetDay> {
  val days = mutableListOf<JetDay>()
  days.add(startDate.toJetDay(this))
  while (days.size != 7) {
    days.add(days.last().nextDay(this))
  }
  return days
}

fun LocalDate.toJetDay(jetWeek: JetWeek): JetDay {
  return JetDay(this, this.monthValue == jetWeek.monthOfWeek)
}

private fun JetDay.nextDay(jetWeek: JetWeek): JetDay {
  val date = this.date.plusDays(1)
  val isPartOfMonth = this.date.plusDays(1).monthValue == jetWeek.monthOfWeek
  return JetDay(date, isPartOfMonth)
}

fun JetWeek.nextWeek(isFirstWeek: Boolean): JetWeek {
  val firstDay = this.endDate.plusDays(1)
  val lastDay = this.endDate.plusDays(7)
  return JetWeek(
    firstDay,
    lastDay,
    monthOfWeek = monthOfWeek,
    dayOfWeek = dayOfWeek,
    isFirstWeek = isFirstWeek
  )
}

