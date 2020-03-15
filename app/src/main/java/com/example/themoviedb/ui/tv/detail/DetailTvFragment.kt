package com.example.themoviedb.ui.tv.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.themoviedb.R
import com.example.themoviedb.extension.toImageInfo
import com.example.themoviedb.extension.seasonsToImagesInfos
import com.example.themoviedb.extension.toDetailInformation
import com.example.themoviedb.extension.tvsToImagesInfos
import com.example.themoviedb.ui.widget.Detail
import com.example.themoviedb.ui.widget.CastList
import com.example.themoviedb.ui.widget.ImageInfoList
import com.example.themoviedb.ui.widget.OnFavorite
import com.example.themoviedb.ui.widget.OnWish
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTvFragment: Fragment(R.layout.fragment_tv_detail) {

    private lateinit var detail: Detail
    private lateinit var cast: CastList
    private lateinit var seasons: ImageInfoList
    private lateinit var similarTvShows: ImageInfoList
    private lateinit var navController: NavController
    private val args: DetailTvFragmentArgs by navArgs()
    private val tvViewModel: DetailTvViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setupViews(view)
        setupObservables()
    }

    override fun onStart() {
        super.onStart()
        tvViewModel.setIdTv(args.idTv)
        fetchData()
    }

    private fun setupObservables() {
        observeTvCast()
        observeTvDetail()
        observeTvSimilar()
        observeFavorite()
        observeWish()
    }

    private fun observeTvCast() {
        tvViewModel.cast
            .observeLoading(viewLifecycleOwner) {
                cast.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                cast.onData(it.cast)
            }
            .observeError(viewLifecycleOwner) { throwable ->
                cast.onError()
            }
    }

    private fun observeTvDetail() {
        tvViewModel.tvsDetail
            .observeLoading(viewLifecycleOwner) {
                detail.onLoading(it)
                seasons.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                seasons.onData(
                    it.seasons.seasonsToImagesInfos(),
                    context!!.getString(R.string.no_content_text_seasons)
                )
                detail.onData(it.toDetailInformation())
            }
            .observeError(viewLifecycleOwner) { throwable ->
                detail.onError()
            }
    }

    private fun observeTvSimilar() {
        tvViewModel.similarTvs
            .observeLoading(viewLifecycleOwner) {
                similarTvShows.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                similarTvShows.onData(
                    it.results.tvsToImagesInfos(),
                    context!!.getString(R.string.no_content_text_shows_similar))
                {
                    navController.navigate(
                        DetailTvFragmentDirections.actionTvDetailFragmentSelf(it.id)
                    )
                }
            }
            .observeError(viewLifecycleOwner) { throwable ->
                similarTvShows.onError()
            }
    }

    private fun observeFavorite() {
        tvViewModel.favorite
            .observeData(viewLifecycleOwner) {
                val onFavorite: OnFavorite = {
                    tvViewModel.insertFavorite(it.toImageInfo())
                }

                val offFavorite: OnFavorite = {
                    tvViewModel.deleteFavorite()
                }

                detail.setupFavorite(it, onFavorite, offFavorite)
            }
    }

    private fun observeWish() {
        tvViewModel.wish
            .observeData(viewLifecycleOwner) {
                val onWish: OnWish = {
                    tvViewModel.insertWish(it.toImageInfo())
                }

                val offWish: OnWish = {
                    tvViewModel.deleteWish()
                }

                detail.setupWish(it, onWish, offWish)
            }
    }

    private fun fetchData() {
        fetchTvCast()
        fetchTvDetail()
        fetchTvSimilar()
        fetchFavorite()
        fetchWish()

        detail.onErrorClickListener {
            fetchTvDetail()
        }
        similarTvShows.onErrorClickListener {
            fetchTvSimilar()
        }
        cast.onErrorClickListener {
            fetchTvCast()
        }
    }

    private fun fetchWish() {
        tvViewModel.isWish()
    }

    private fun fetchFavorite() {
        tvViewModel.isFavorite()
    }

    private fun fetchTvDetail() {
        tvViewModel.tvDetail()
    }

    private fun fetchTvCast() {
        tvViewModel.castTv()
    }

    private fun fetchTvSimilar() {
        tvViewModel.similarTv()
    }

    private fun setupViews(view: View) {
        detail = view.findViewById(R.id.detail)
        cast = view.findViewById(R.id.cast)
        similarTvShows = view.findViewById(R.id.similar)
        seasons = view.findViewById(R.id.seasons)
    }
}