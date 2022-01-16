package dev.baseio.libjetcalendar.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.LocalDate

class JetPagingSource(private val initialYear: LocalDate, private val firstDayOfWeek: DayOfWeek) : PagingSource<LocalDate, JetYear>() {

  override suspend fun load(params: LoadParams<LocalDate>): LoadResult<LocalDate, JetYear> {
    val initial = params.key ?: initialYear
    return LoadResult.Page(
      data = withContext(Dispatchers.Default) {
        mutableListOf<JetYear>().apply {
          add(JetYear.current(initial,firstDayOfWeek))
          while (this.size != params.loadSize) {
            add(JetYear.current(this.last().endDate.plusDays(1), firstDayOfWeek))
          }
        }
      },
      prevKey = null,
      nextKey = initial.plusYears(1)
    )
  }

  override fun getRefreshKey(state: PagingState<LocalDate, JetYear>): LocalDate? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestItemToPosition(anchorPosition)?.startDate
    }
  }
}