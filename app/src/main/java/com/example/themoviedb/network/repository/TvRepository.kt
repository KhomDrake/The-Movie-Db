package com.example.themoviedb.network.repository

import com.example.themoviedb.network.Api

class TvRepository(private val api: Api) {

    suspend fun latestTv() = api.latestTv()

    suspend fun tvAiringTodayTv() = api.tvAiringToday()

    suspend fun tvOnTheAirTv() = api.tvOnTheAir()

    suspend fun popularTv() = api.popularTv()

    suspend fun topRatedTv() = api.topRatedTv()

    suspend fun tvDetail(idTv: Int) = api.tvDetail(idTv)

    suspend fun castTv(idTv: Int) = api.tvCast(idTv)

    suspend fun similarTv(idTv: Int) = api.tvSimilar(idTv)
}