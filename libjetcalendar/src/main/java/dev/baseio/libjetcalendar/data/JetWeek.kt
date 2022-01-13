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
) : Parcelable {
  companion object {
    fun current(date: LocalDate = LocalDate.now()): JetWeek {
      val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
      val startOfCurrentWeek: LocalDate = date.with(TemporalAdjusters.previousOrSame(firstDayOfWeek))
      val lastDayOfWeek = firstDayOfWeek.plus(6) // or minus(1)
      val endOfWeek: LocalDate = date.with(TemporalAdjusters.nextOrSame(lastDayOfWeek))
      return JetWeek(startOfCurrentWeek, endOfWeek, date.monthValue)
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

fun LocalDate.toJetDay(jetWeek: JetWeek): JetDay {
  return JetDay(this, this.monthValue == jetWeek.monthOfWeek)
}

private fun JetDay.nextDay(jetWeek: JetWeek): JetDay {
  return JetDay(this.date.plusDays(1), this.date.plusDays(1).monthValue == jetWeek.monthOfWeek)
}

fun JetWeek.nextWeek(): JetWeek {
  return JetWeek(this.endDate.plusDays(1), this.endDate.plusDays(7), monthOfWeek = monthOfWeek)
}

