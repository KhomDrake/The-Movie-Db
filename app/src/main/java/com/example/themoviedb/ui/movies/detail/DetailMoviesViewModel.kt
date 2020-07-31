package com.example.themoviedb.ui.movies.detail

import androidx.lifecycle.ViewModel
import com.example.themoviedb.common.livedata.MutableStateLiveData
import com.example.themoviedb.common.livedata.StateLiveData
import com.example.themoviedb.network.coroutine
import com.example.themoviedb.extension.toTableFavorite
import com.example.themoviedb.extension.toTableWish
import com.example.themoviedb.models.ImageInfoType
import com.example.themoviedb.network.repository.MovieRepository
import com.example.themoviedb.models.network.Movie
import com.example.themoviedb.models.network.Credit
import com.example.themoviedb.models.application.ImageInfo
import com.example.themoviedb.models.network.MovieDetail
import com.example.themoviedb.models.network.Result
import com.example.themoviedb.network.repository.LibraryRepository

class DetailMoviesViewModel(
    private val repository: MovieRepository,
    private val libraryRepository: LibraryRepository
) : ViewModel() {

    private val _movieDetail: MutableStateLiveData<MovieDetail> = MutableStateLiveData()
    private val _cast: MutableStateLiveData<Credit> = MutableStateLiveData()
    private val _similarMovies: MutableStateLiveData<Result<Movie>> = MutableStateLiveData()
    private val _wish: MutableStateLiveData<Boolean> = MutableStateLiveData()
    private val _favorite: MutableStateLiveData<Boolean> = MutableStateLiveData()

    val movieDetail: StateLiveData<MovieDetail>
        get() = _movieDetail

    val cast: StateLiveData<Credit>
        get() = _cast

    val similarMovies: MutableStateLiveData<Result<Movie>>
        get() = _similarMovies

    val wish: MutableStateLiveData<Boolean>
        get() = _wish

    val favorite: MutableStateLiveData<Boolean>
        get() = _favorite

    private var idMovie: Int = 0

    fun setIdMovie(id: Int) {
        idMovie = id
    }

    fun movieDetail() = coroutine(_movieDetail) {
        repository.movieDetail(idMovie)
    }

    fun castMovie() = coroutine(_cast) {
        repository.movieCast(idMovie)
    }

    fun similarMovies() = coroutine(_similarMovies) {
        repository.similarMovies(idMovie)
    }

    fun insertWish(imageInfo: ImageInfo) = coroutine {
        libraryRepository.insertWish(imageInfo.toTableWish(ImageInfoType.MOVIE))
    }

    fun deleteWish() = coroutine {
        libraryRepository.deleteWish(idMovie)
    }

    fun insertFavorite(imageInfo: ImageInfo) = coroutine {
        libraryRepository.insertFavorite(imageInfo.toTableFavorite(ImageInfoType.MOVIE))
    }

    fun deleteFavorite() = coroutine {
        libraryRepository.deleteFavorite(idMovie)
    }

    fun isWish() = coroutine(wish) {
        libraryRepository.getWish(idMovie).isNotEmpty()
    }

    fun isFavorite() = coroutine(favorite) {
        libraryRepository.getFavorite(idMovie).isNotEmpty()
    }
}