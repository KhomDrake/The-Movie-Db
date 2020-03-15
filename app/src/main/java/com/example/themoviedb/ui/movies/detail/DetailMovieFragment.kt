package com.example.themoviedb.ui.movies.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.themoviedb.R
import com.example.themoviedb.extension.toDetailInformation
import com.example.themoviedb.extension.moviesToImagesInfos
import com.example.themoviedb.extension.toImageInfo
import com.example.themoviedb.ui.widget.Detail
import com.example.themoviedb.ui.widget.CastList
import com.example.themoviedb.ui.widget.ImageInfoList
import com.example.themoviedb.ui.widget.OnFavorite
import com.example.themoviedb.ui.widget.OnWish
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieFragment : Fragment(R.layout.fragment_movie_detail) {

    private lateinit var detail: Detail
    private lateinit var cast: CastList
    private lateinit var similarMovies: ImageInfoList
    private val args: DetailMovieFragmentArgs by navArgs()
    private lateinit var navController: NavController
    private val detailMoviesViewModel: DetailMoviesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setupViews(view)
        setupObservables()
    }

    override fun onStart() {
        super.onStart()
        detailMoviesViewModel.setIdMovie(args.idMovie)
        fetchData()
    }

    private fun setupObservables() {
        observeCast()
        observeFavorite()
        observeMovieDetail()
        observeSimilarMovies()
        observeWish()
    }

    private fun observeMovieDetail() {
        detailMoviesViewModel.movieDetail
            .observeLoading(viewLifecycleOwner) {
                detail.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                detail.onData(it.toDetailInformation())
            }
            .observeError(viewLifecycleOwner) {
                detail.onError()
            }
    }

    private fun observeSimilarMovies() {
        detailMoviesViewModel.similarMovies
            .observeLoading(viewLifecycleOwner) {
                similarMovies.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                similarMovies.onData(
                    it.results.moviesToImagesInfos(),
                    context!!.getString(R.string.no_content_text_movies_similar)
                ) {
                    navController.navigate(
                        DetailMovieFragmentDirections.actionMovieDetailFragmentSelf(
                            it.id
                        )
                    )
                }
            }
            .observeError(viewLifecycleOwner) {
                similarMovies.onError()
            }
    }

    private fun observeCast() {
        detailMoviesViewModel.cast.
            observeLoading(viewLifecycleOwner) {
                cast.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                cast.onData(it.cast)
            }
            .observeError(viewLifecycleOwner) {
                cast.onError()
            }
    }

    private fun observeWish() {
        detailMoviesViewModel.wish
            .observeData(viewLifecycleOwner) {
                val onWish: OnWish = { detailInformation ->
                    detailMoviesViewModel.insertWish(detailInformation.toImageInfo())
                }

                val offWish: OnWish = {
                    detailMoviesViewModel.deleteWish()
                }

                detail.setupWish(it, onWish, offWish)
            }
    }

    private fun observeFavorite() {
        detailMoviesViewModel.favorite
            .observeData(viewLifecycleOwner) {
                val onFavorite: OnFavorite = { detailInformation ->
                    detailMoviesViewModel.insertFavorite(detailInformation.toImageInfo())
                }

                val offFavorite: OnFavorite = {
                    detailMoviesViewModel.deleteFavorite()
                }

                detail.setupFavorite(it, onFavorite, offFavorite)
            }
    }

    private fun fetchData() {
        fetchWish()
        fetchFavorite()
        fetchMovieCast()
        fetchMovieDetail()
        fetchSimilarMovies()

        detail.onErrorClickListener {
            fetchMovieDetail()
        }
        cast.onErrorClickListener {
            fetchMovieCast()
        }
        similarMovies.onErrorClickListener {
            fetchSimilarMovies()
        }
    }

    private fun fetchWish() {
        detailMoviesViewModel.isWish()
    }

    private fun fetchFavorite() {
        detailMoviesViewModel.isFavorite()
    }

    private fun fetchMovieCast() {
        detailMoviesViewModel.castMovie()
    }

    private fun fetchMovieDetail() {
        detailMoviesViewModel.movieDetail()
    }

    private fun fetchSimilarMovies() {
        detailMoviesViewModel.similarMovies()
    }

    private fun setupViews(view: View) {
        detail = view.findViewById(R.id.detail)
        cast = view.findViewById(R.id.cast)
        similarMovies = view.findViewById(R.id.similar)
    }
}