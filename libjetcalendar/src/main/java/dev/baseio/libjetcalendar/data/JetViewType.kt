package dev.baseio.libjetcalendar.data

enum class JetViewType {
  MONTHLY,
  WEEKLY,
  BI_WEEKLY,
  YEARLY;

  fun next(): JetViewType {
    if (ordinal == 3) {
      return MONTHLY
    }
    return values()[ordinal + 1]
  }
}