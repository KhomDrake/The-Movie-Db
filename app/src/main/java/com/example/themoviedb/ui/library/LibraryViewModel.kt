package com.example.themoviedb.ui.library

import androidx.lifecycle.ViewModel
import com.example.themoviedb.common.livedata.MutableStateLiveData
import com.example.themoviedb.common.livedata.StateLiveData
import com.example.themoviedb.network.coroutine
import com.example.themoviedb.models.application.ImageInfo
import com.example.themoviedb.network.repository.LibraryRepository

class LibraryViewModel(private val libraryRepository: LibraryRepository) : ViewModel() {

    private val _favorites: MutableStateLiveData<List<ImageInfo>> = MutableStateLiveData()
    private val _wish: MutableStateLiveData<List<ImageInfo>> = MutableStateLiveData()

    val favorites: StateLiveData<List<ImageInfo>>
        get() = _favorites

    val wish: StateLiveData<List<ImageInfo>>
        get() = _wish


    fun favorites() = coroutine(_favorites) {
        libraryRepository.favorites()
    }

    fun wish() = coroutine(_wish) {
        libraryRepository.wish()
    }

}