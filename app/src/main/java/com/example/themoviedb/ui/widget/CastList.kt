package com.example.themoviedb.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.R
import com.example.themoviedb.extension.gone
import com.example.themoviedb.extension.visible
import com.example.themoviedb.models.network.Cast
import com.example.themoviedb.ui.adapter.RecyclerViewAdapterCast
import com.facebook.shimmer.ShimmerFrameLayout

class CastList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val imagesViewRecyclerView: RecyclerView
    private val imagesShimmer: ShimmerFrameLayout
    private val errorButton: Button
    private val noContent: AppCompatTextView

    init {
        inflate(context, R.layout.cast_list, this)
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
        }
    }

    fun onData(cast: List<Cast>) {
        imagesShimmer.gone()
        errorButton.gone()
        imagesViewRecyclerView.visible()
        setupData(cast)
    }

    fun onError() {
        imagesShimmer.gone()
        errorButton.visible()
        imagesViewRecyclerView.gone()
    }

    fun onErrorClickListener(listener: () -> Unit) {
        errorButton.setOnClickListener { listener.invoke() }
    }

    private fun setupData(cast: List<Cast>) {
        if(cast.isEmpty()) noContent.visible() else noContent.gone()

        imagesViewRecyclerView.adapter =
            RecyclerViewAdapterCast(cast)

        imagesViewRecyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }
}