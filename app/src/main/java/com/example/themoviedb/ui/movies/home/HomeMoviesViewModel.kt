package com.example.themoviedb.ui.movies.home

import androidx.lifecycle.ViewModel
import com.example.themoviedb.common.livedata.MutableStateLiveData
import com.example.themoviedb.common.livedata.StateLiveData
import com.example.themoviedb.network.coroutine
import com.example.themoviedb.network.repository.MovieRepository
import com.example.themoviedb.models.Category
import com.example.themoviedb.models.network.Movie
import com.example.themoviedb.models.network.Result

class HomeMoviesViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _latest: MutableStateLiveData<Movie> = MutableStateLiveData()
    private val _upcoming: MutableStateLiveData<Result<Movie>> = MutableStateLiveData()
    private val _nowPlaying: MutableStateLiveData<Result<Movie>> = MutableStateLiveData()
    private val _popular: MutableStateLiveData<Result<Movie>> = MutableStateLiveData()
    private val _topRated: MutableStateLiveData<Result<Movie>> = MutableStateLiveData()

    val latest: StateLiveData<Movie>
        get() = _latest

    val upcoming: StateLiveData<Result<Movie>>
        get() = _upcoming

    val nowPlaying: StateLiveData<Result<Movie>>
        get() = _nowPlaying

    val popular: StateLiveData<Result<Movie>>
        get() = _popular

    val topRated: StateLiveData<Result<Movie>>
        get() = _topRated

    fun fetchMoviesCategory(category: Category) = when(category) {
        Category.Upcoming -> moviesUpcoming()
        Category.NowPlaying -> moviesNowPlaying()
        Category.Popular -> moviesPopular()
        Category.TopRated -> moviesTopRated()
        Category.Latest -> movieLatest()
    }

    private fun moviesNowPlaying() =
        coroutine(_nowPlaying) {
            repository.moviesNowPlaying()
        }

    private fun movieLatest() = coroutine(_latest) {
        repository.movieLatest()
    }

    private fun moviesPopular() = coroutine(_popular) {
        repository.moviesPopular()
    }

    private fun moviesTopRated() = coroutine(_topRated) {
        repository.moviesTopRated()
    }

    private fun moviesUpcoming() = coroutine(_upcoming) {
        repository.moviesUpcoming()
    }
}