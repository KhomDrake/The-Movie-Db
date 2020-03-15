package com.example.themoviedb.ui.tv.detail

import androidx.lifecycle.ViewModel
import com.example.themoviedb.common.livedata.MutableStateLiveData
import com.example.themoviedb.common.livedata.StateLiveData
import com.example.themoviedb.network.makeAsyncOperation
import com.example.themoviedb.extension.toTableFavorite
import com.example.themoviedb.extension.toTableWish
import com.example.themoviedb.models.ImageInfoType
import com.example.themoviedb.network.repository.TvRepository
import com.example.themoviedb.models.network.TvDetail
import com.example.themoviedb.models.network.Credit
import com.example.themoviedb.models.network.TV
import com.example.themoviedb.models.network.Result
import com.example.themoviedb.models.application.ImageInfo
import com.example.themoviedb.network.repository.LibraryRepository

class DetailTvViewModel(
    private val tvRepository: TvRepository,
    private val libraryRepository: LibraryRepository
) : ViewModel() {

    private val _tvDetail: MutableStateLiveData<TvDetail> = MutableStateLiveData()
    private val _cast: MutableStateLiveData<Credit> = MutableStateLiveData()
    private val _similarTvs: MutableStateLiveData<Result<TV>> = MutableStateLiveData()
    private val _wish: MutableStateLiveData<Boolean> = MutableStateLiveData()
    private val _favorite: MutableStateLiveData<Boolean> = MutableStateLiveData()

    val tvsDetail: StateLiveData<TvDetail>
        get() = _tvDetail

    val cast: StateLiveData<Credit>
        get() = _cast

    val similarTvs: MutableStateLiveData<Result<TV>>
        get() = _similarTvs

    val wish: MutableStateLiveData<Boolean>
        get() = _wish

    val favorite: MutableStateLiveData<Boolean>
        get() = _favorite

    private var idTv = 0

    fun setIdTv(id: Int) {
        idTv = id
    }

    fun insertWish(imageInfo: ImageInfo) = makeAsyncOperation {
        libraryRepository.insertWish(imageInfo.toTableWish(ImageInfoType.TV))
    }

    fun deleteWish() = makeAsyncOperation {
        libraryRepository.deleteWish(idTv)
    }

    fun insertFavorite(imageInfo: ImageInfo) = makeAsyncOperation {
        libraryRepository.insertFavorite(imageInfo.toTableFavorite(ImageInfoType.TV))
    }

    fun deleteFavorite() = makeAsyncOperation {
        libraryRepository.deleteFavorite(idTv)
    }

    fun isWish() = makeAsyncOperation(_wish) {
        libraryRepository.getWish(idTv).isNotEmpty()
    }

    fun isFavorite() = makeAsyncOperation(_favorite) {
        libraryRepository.getFavorite(idTv).isNotEmpty()
    }

    fun tvDetail() = makeAsyncOperation(_tvDetail) {
        tvRepository.tvDetail(idTv)
    }

    fun castTv() = makeAsyncOperation(_cast) {
        tvRepository.castTv(idTv)
    }

    fun similarTv() = makeAsyncOperation(_similarTvs) {
        tvRepository.similarTv(idTv)
    }

}