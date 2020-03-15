package com.example.themoviedb.ui.library

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.themoviedb.R
import com.example.themoviedb.models.ImageInfoType
import com.example.themoviedb.models.application.ImageInfo
import com.example.themoviedb.ui.widget.ImageInfoList
import org.koin.androidx.viewmodel.ext.android.viewModel

class LibraryFragment : Fragment(R.layout.fragment_library) {

    private val libraryViewModel: LibraryViewModel by viewModel()
    private lateinit var navController: NavController
    private lateinit var favorite: ImageInfoList
    private lateinit var wish: ImageInfoList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setupView(view)
        setupObservables()
    }

    override fun onStart() {
        super.onStart()
        fetchData()
    }

    private fun setupObservables() {
        observeWish()
        observeFavorite()
    }

    private fun observeFavorite() {
        libraryViewModel.favorites
            .observeLoading(viewLifecycleOwner) {
                favorite.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                favorite.onData(it, context!!.getString(R.string.no_content_text_favorite_list)) {
                    goToDetail(it)
                }
            }
            .observeError(viewLifecycleOwner) {
                favorite.onError()
            }
    }

    private fun observeWish() {
        libraryViewModel.wish
            .observeLoading(viewLifecycleOwner) {
                wish.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                wish.onData(it, context!!.getString(R.string.no_content_text_wish_list)) {
                    goToDetail(it)
                }
            }
            .observeError(viewLifecycleOwner) {
                wish.onError()
            }
    }

    private fun fetchData() {
        fetchFavorite()
        fetchWish()
        wish.onErrorClickListener {
            fetchFavorite()
        }
        favorite.onErrorClickListener {
            fetchWish()
        }
    }

    private fun fetchWish() {
        libraryViewModel.wish()
    }

    private fun fetchFavorite() {
        libraryViewModel.favorites()
    }

    private fun goToDetail(imageInfo: ImageInfo) {
        imageInfo.imageInfoType?.apply {
            if(this == ImageInfoType.TV)
                navController.navigate(
                    LibraryFragmentDirections.actionNavigationLibraryToTvDetailFragment(imageInfo.id)
                )
            else
                navController.navigate(
                    LibraryFragmentDirections.actionNavigationLibraryToMovieDetailFragment(imageInfo.id)
                )
        }
    }

    private fun setupView(view: View) {
        favorite = view.findViewById(R.id.favorite)
        wish = view.findViewById(R.id.wish)
    }
}