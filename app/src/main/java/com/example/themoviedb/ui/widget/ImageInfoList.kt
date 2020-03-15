package com.example.themoviedb.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.R
import com.example.themoviedb.extension.gone
import com.example.themoviedb.extension.visible
import com.example.themoviedb.models.application.ImageInfo
import com.example.themoviedb.ui.adapter.RecyclerViewAdapterImageList
import com.facebook.shimmer.ShimmerFrameLayout

class ImageInfoList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val imagesViewRecyclerView: RecyclerView
    private val imagesShimmer: ShimmerFrameLayout
    private val errorButton: Button
    private val noContent: TextView

    init {
        inflate(context, R.layout.image_info_list, this)
        imagesViewRecyclerView = findViewById(R.id.recyclerview)
        imagesShimmer = findViewById(R.id.list_of_images_info)
        errorButton = findViewById(R.id.error_button)
        noContent = findViewById(R.id.no_content)
        onLoading(loading = true)
    }

    fun onLoading(loading: Boolean) {
        if(loading) {
            imagesShimmer.visible()
            errorButton.gone()
            imagesViewRecyclerView.gone()
            noContent.gone()
        } else {
            imagesShimmer.gone()
        }
    }

    fun onData(
        imagesInfo: List<ImageInfo>,
        noContentText: String = context.getString(R.string.no_content_text_default),
        onClickImageInfo: (ImageInfo) -> Unit = {}
    ) {
        imagesShimmer.gone()
        errorButton.gone()
        imagesViewRecyclerView.visible()
        noContent.text = noContentText

        if(imagesInfo.isNotEmpty()) {
            setupData(imagesInfo, onClickImageInfo)
            noContent.gone()
            imagesViewRecyclerView.visible()
        }
        else {
            noContent.visible()
            imagesViewRecyclerView.gone()
        }
    }

    fun onError() {
        imagesShimmer.gone()
        errorButton.visible()
        imagesViewRecyclerView.gone()
    }

    fun onErrorClickListener(listener: () -> Unit) {
        errorButton.setOnClickListener { listener.invoke() }
    }

    private fun setupData(imagesInfo: List<ImageInfo>, onClickImageInfo: (ImageInfo) -> Unit) {
        imagesViewRecyclerView.adapter =
            RecyclerViewAdapterImageList(
                imagesInfo,
                onClickImageInfo
            )
        imagesViewRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }
}