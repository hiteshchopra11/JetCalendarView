package dev.baseio.libjetcalendar.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.time.LocalDate

class JetPagingSource(private val initialDate:LocalDate) : PagingSource<LocalDate, JetMonth>() {

  override suspend fun load(params: LoadParams<LocalDate>): LoadResult<LocalDate, JetMonth> {
    val initial = params.key ?: initialDate
    return LoadResult.Page(
      data = JetYear.current(initial).months(),
      prevKey = null,
      nextKey = initial.plusYears(1)
    )
  }

  override fun getRefreshKey(state: PagingState<LocalDate, JetMonth>): LocalDate? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestItemToPosition(anchorPosition)?.startDate
    }
  }
}