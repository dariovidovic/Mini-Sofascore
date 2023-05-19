package com.example.mini_sofascore.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mini_sofascore.data.Match
import com.example.mini_sofascore.retrofit.RetrofitHelper
import retrofit2.Response

class MatchesPagingSource(private val tournamentId: Int) :
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
        var matchesResponse: Response<List<Match>>
        if(prevKey<0){
            matchesResponse =
                RetrofitHelper.getRetrofitInstance().getTournamentMatches(id = tournamentId, span = "last", page = -pageIndex)
        }
        else{
            matchesResponse =
                RetrofitHelper.getRetrofitInstance().getTournamentMatches(id = tournamentId, span = "next", page = pageIndex)
        }

        val matches = matchesResponse.body()?: arrayListOf()
        return LoadResult.Page(data = matches, prevKey = prevKey, nextKey = nextKey)

    }
}