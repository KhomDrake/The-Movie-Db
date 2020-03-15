package com.example.themoviedb.ui.movies.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.themoviedb.R
import com.example.themoviedb.common.livedata.StateLiveData
import com.example.themoviedb.extension.moviesToImagesInfos
import com.example.themoviedb.extension.toImageInfo
import com.example.themoviedb.models.network.Movie
import com.example.themoviedb.models.network.Result
import com.example.themoviedb.models.Category
import com.example.themoviedb.models.application.ImageInfo
import com.example.themoviedb.ui.widget.ImageInfoList
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeMoviesFragment : Fragment(R.layout.fragment_home_movies) {

    private val homeMoviesViewModel: HomeMoviesViewModel by viewModel()
    private lateinit var navController: NavController
    private val detail = { image: ImageInfo ->
        navController.navigate(
            HomeMoviesFragmentDirections.actionNavigationCinemaToMovieDetailFragment(
                image.id
            )
        )
    }
    private lateinit var imagesInfoList: List<ImageInfoList>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setupViews(view)
        setupObservables()
    }

    override fun onStart() {
        super.onStart()
        fetchMovies()
    }

    private fun setupObservables() {
        observeCategory(Category.Upcoming)
        observeCategory(Category.NowPlaying)
        observeCategory(Category.Popular)
        observeCategory(Category.TopRated)
        observeCategory(Category.Latest)
    }

    private fun observeCategory(category: Category) {
        val imageInfoList = imagesInfoList[category.ordinal]

        imageInfoList.onErrorClickListener { fetchCategory(category) }

        when(category) {
            Category.Latest -> observeMovie(homeMoviesViewModel.latest, imageInfoList)
            Category.NowPlaying -> observeMovies(homeMoviesViewModel.nowPlaying, imageInfoList)
            Category.Popular -> observeMovies(homeMoviesViewModel.popular, imageInfoList)
            Category.TopRated -> observeMovies(homeMoviesViewModel.topRated, imageInfoList)
            Category.Upcoming -> observeMovies(homeMoviesViewModel.upcoming, imageInfoList)
        }
    }

    private fun observeMovie(movie: StateLiveData<Movie>, imageInfoList: ImageInfoList) {
        movie
            .observeLoading(viewLifecycleOwner) {
                imageInfoList.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                imageInfoList.onData(
                    listOf(it.toImageInfo()),
                    context!!.getString(R.string.no_content_text_movies),
                    detail
                )
            }
            .observeError(viewLifecycleOwner) { t ->
                imageInfoList.onError()
            }
    }

    private fun observeMovies(movies: StateLiveData<Result<Movie>>, imageInfoList: ImageInfoList) {
        movies
            .observeLoading(viewLifecycleOwner) {
                imageInfoList.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                imageInfoList.onData(
                    it.results.moviesToImagesInfos(),
                    context!!.getString(R.string.no_content_text_movies),
                    detail
                )
            }
            .observeError(viewLifecycleOwner) { t ->
                imageInfoList.onError()
            }
    }

    private fun setupViews(view: View) {
        imagesInfoList = listOf(
            view.findViewById(R.id.latest),
            view.findViewById(R.id.now_playing),
            view.findViewById(R.id.popular),
            view.findViewById(R.id.top_rated),
            view.findViewById(R.id.upcoming)
        )
    }

    private fun fetchMovies() {
        fetchCategory(Category.Latest)
        fetchCategory(Category.Popular)
        fetchCategory(Category.TopRated)
        fetchCategory(Category.NowPlaying)
        fetchCategory(Category.Upcoming)
    }

    private fun fetchCategory(category: Category) {
        homeMoviesViewModel.fetchMoviesCategory(category)
    }
}