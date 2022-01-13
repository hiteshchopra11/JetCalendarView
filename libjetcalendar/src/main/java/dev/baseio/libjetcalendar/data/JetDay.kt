package dev.baseio.libjetcalendar.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JetDay(val year: Int, val month: Int, val day: Int,val isPartOfMonth:Boolean): Parcelable