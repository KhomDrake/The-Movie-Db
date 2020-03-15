package com.example.themoviedb.ui.tv.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.themoviedb.R
import com.example.themoviedb.common.livedata.StateLiveData
import com.example.themoviedb.extension.toImageInfo
import com.example.themoviedb.extension.tvsToImagesInfos
import com.example.themoviedb.models.network.Result
import com.example.themoviedb.models.network.TV
import com.example.themoviedb.models.Category
import com.example.themoviedb.models.application.ImageInfo
import com.example.themoviedb.ui.widget.ImageInfoList
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeTvFragment : Fragment(R.layout.fragment_home_tv) {

    private val homeTvViewModel: HomeTvViewModel by viewModel()
    private lateinit var navController: NavController
    private val detail: (ImageInfo) -> Unit = {
        navController.navigate(
            HomeTvFragmentDirections.actionNavigationTvToTvDetailFragment(it.id)
        )
    }
    private lateinit var imagesInfo: List<ImageInfoList>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setupView(view)
        setupObservables()
    }

    override fun onStart() {
        super.onStart()
        fetchTvs()
    }

    private fun setupObservables() {
        observeCategory(Category.Upcoming)
        observeCategory(Category.NowPlaying)
        observeCategory(Category.Popular)
        observeCategory(Category.TopRated)
        observeCategory(Category.Latest)
    }

    private fun observeCategory(category: Category) {
        val imageInfoList = imagesInfo[category.ordinal]

        imageInfoList.onErrorClickListener { fetchCategory(category) }

        when(category) {
            Category.Latest -> observeTv(homeTvViewModel.latest, imageInfoList)
            Category.NowPlaying -> observeTvs(homeTvViewModel.tvOnTheAirTv, imageInfoList)
            Category.Popular -> observeTvs(homeTvViewModel.popular, imageInfoList)
            Category.TopRated -> observeTvs(homeTvViewModel.topRated, imageInfoList)
            Category.Upcoming -> observeTvs(homeTvViewModel.tvAiringTodayTv, imageInfoList)
        }
    }

    private fun observeTv(tv: StateLiveData<TV>, imageInfoList: ImageInfoList) {
        tv
            .observeLoading(viewLifecycleOwner) {
                imageInfoList.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                imageInfoList.onData(
                    listOf(it.toImageInfo()),
                    context!!.getString(R.string.no_content_text_shows),
                    detail
                )
            }
            .observeError(viewLifecycleOwner) {
                imageInfoList.onError()
            }
    }

    private fun observeTvs(tvs: StateLiveData<Result<TV>>, imageInfoList: ImageInfoList) {
        tvs
            .observeLoading(viewLifecycleOwner) {
                imageInfoList.onLoading(it)
            }
            .observeData(viewLifecycleOwner) {
                imageInfoList.onData(
                    it.results.tvsToImagesInfos(),
                    context!!.getString(R.string.no_content_text_shows),
                    detail
                )
            }
            .observeError(viewLifecycleOwner) {
                imageInfoList.onError()
            }
    }

    private fun setupView(view: View) {
        imagesInfo = listOf(
            view.findViewById(R.id.latest),
            view.findViewById(R.id.tv_airing_today),
            view.findViewById(R.id.tv_on_the_air),
            view.findViewById(R.id.popular),
            view.findViewById(R.id.top_rated)
        )
    }

    private fun fetchTvs() {
        fetchCategory(Category.Latest)
        fetchCategory(Category.NowPlaying)
        fetchCategory(Category.Upcoming)
        fetchCategory(Category.Popular)
        fetchCategory(Category.TopRated)
    }

    private fun fetchCategory(category: Category) {
        homeTvViewModel.fetchTvs(category)
    }
}