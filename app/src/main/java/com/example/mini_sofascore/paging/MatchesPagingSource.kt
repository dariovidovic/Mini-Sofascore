package com.example.mini_sofascore.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.retrofit.RetrofitHelper
import com.example.mini_sofascore.utils.Type
import retrofit2.Response

class MatchesPagingSource(private val id: Int, private val type: String) :
    PagingSource<Int, Match>() {
    override fun getRefreshKey(state: PagingState<Int, Match>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Match> {
        val pageIndex = params.key ?: 0
        val prevKey = pageIndex - 1
        val nextKey = pageIndex + 1
        val matchesResponse: Response<List<Match>>


        when (type) {
            Type.TOURNAMENT -> {
                matchesResponse = if (prevKey < 0) {
                    RetrofitHelper.getRetrofitInstance().getTournamentMatches(
                        id = id,
                        span = "last",
                        page = -pageIndex
                    )
                } else {
                    RetrofitHelper.getRetrofitInstance().getTournamentMatches(
                        id = id,
                        span = "next",
                        page = pageIndex
                    )
                }
            }
            Type.TEAM -> {
                matchesResponse = if (prevKey < 0) {
                    RetrofitHelper.getRetrofitInstance()
                        .getTeamMatches(id = id, span = "last", page = -pageIndex)
                } else {
                    RetrofitHelper.getRetrofitInstance()
                        .getTeamMatches(id = id, span = "next", page = pageIndex)
                }
            }

            Type.PLAYER -> {
                matchesResponse = if (prevKey < 0) {
                    RetrofitHelper.getRetrofitInstance()
                        .getPlayerMatches(id = id, span = "last", page = -pageIndex)
                } else {
                    RetrofitHelper.getRetrofitInstance()
                        .getPlayerMatches(id = id, span = "next", page = pageIndex)
                }
            }

            else -> throw IllegalArgumentException()
        }

        val matches = matchesResponse.body() ?: arrayListOf()
        if (matches.isEmpty())
            return LoadResult.Page(data = matches, prevKey = null, nextKey = null)

        return LoadResult.Page(data = matches, prevKey = prevKey, nextKey = nextKey)

    }
}