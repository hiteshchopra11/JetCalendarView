package dev.baseio.libjetcalendar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class JetDay(val date:LocalDate, val isPartOfMonth: Boolean) : Parcelable