package com.example.themoviedb.ui.tv.home

import androidx.lifecycle.ViewModel
import com.example.themoviedb.common.livedata.MutableStateLiveData
import com.example.themoviedb.common.livedata.StateLiveData
import com.example.themoviedb.network.coroutine
import com.example.themoviedb.network.repository.TvRepository
import com.example.themoviedb.models.Category
import com.example.themoviedb.models.network.Result
import com.example.themoviedb.models.network.TV

class HomeTvViewModel(private val tvRepository: TvRepository) : ViewModel() {

    private val _latest: MutableStateLiveData<TV> = MutableStateLiveData()
    private val _topRated: MutableStateLiveData<Result<TV>> = MutableStateLiveData()
    private val _popular: MutableStateLiveData<Result<TV>> = MutableStateLiveData()
    private val _tvOnTheAirTv: MutableStateLiveData<Result<TV>> = MutableStateLiveData()
    private val _tvAiringTodayTv: MutableStateLiveData<Result<TV>> = MutableStateLiveData()

    val latest: StateLiveData<TV>
        get() = _latest

    val topRated: StateLiveData<Result<TV>>
        get() = _topRated

    val popular: StateLiveData<Result<TV>>
        get() = _popular

    val tvOnTheAirTv: StateLiveData<Result<TV>>
        get() = _tvOnTheAirTv

    val tvAiringTodayTv: StateLiveData<Result<TV>>
        get() = _tvAiringTodayTv

    fun fetchTvs(category: Category) = when(category) {
        Category.Latest -> latestTv()
        Category.TopRated -> topRatedTv()
        Category.Popular -> popularTv()
        Category.Upcoming -> tvOnTheAirTv()
        Category.NowPlaying -> tvAiringTodayTv()
    }

    private fun latestTv() = coroutine(_latest) {
        tvRepository.latestTv()
    }

    private fun tvAiringTodayTv() =
        coroutine(_tvAiringTodayTv) {
            tvRepository.tvAiringTodayTv()
        }

    private fun tvOnTheAirTv() = coroutine(_tvOnTheAirTv) {
        tvRepository.tvOnTheAirTv()
    }

    private fun popularTv() = coroutine(_popular) {
        tvRepository.popularTv()
    }

    private fun topRatedTv() = coroutine(_topRated) {
        tvRepository.topRatedTv()
    }
}