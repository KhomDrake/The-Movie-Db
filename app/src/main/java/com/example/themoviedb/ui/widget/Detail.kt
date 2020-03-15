package com.example.themoviedb.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.extension.gone
import com.example.themoviedb.extension.path
import com.example.themoviedb.extension.visible
import com.example.themoviedb.models.application.DetailInformation
import com.facebook.shimmer.ShimmerFrameLayout

typealias OnFavorite = (DetailInformation) -> Unit
typealias OnWish = (DetailInformation) -> Unit

class Detail @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var detail: DetailInformation
    private lateinit var onWish: OnWish
    private lateinit var offWish: OnWish
    private lateinit var onFavorite: OnFavorite
    private lateinit var offFavorite: OnFavorite
    private var isFavorite: Boolean = false
    private var isWish: Boolean = false
    private val poster: ImageView
    private val backdrop: ImageView
    private val movieTitle: TextView
    private val year: TextView
    private val rate: TextView
    private val description: TextView
    private val detailInformation: List<View>
    private val favorite: Button
    private val wish: Button
    private val errorButton: Button

    init {
        inflate(context, R.layout.detail, this)
        shimmerFrameLayout = findViewById(R.id.shimmer)
        poster = findViewById(R.id.poster)
        backdrop = findViewById(R.id.backdrop)
        movieTitle = findViewById(R.id.title_movie)
        year = findViewById(R.id.year)
        rate = findViewById(R.id.rate)
        description = findViewById(R.id.description)
        favorite = findViewById(R.id.favorite_list)
        wish = findViewById(R.id.wish_list)
        errorButton = findViewById(R.id.error_button)
        detailInformation = listOf(poster, backdrop, movieTitle, year, rate, description, favorite, wish)
    }

    private fun setWish(already: Boolean) {
        updateWish()
        isWish = already
        updateWishText()
    }

    fun setupWish(already: Boolean, onW: OnWish, offW: OnWish) {
        onWish = onW
        offWish = offW
        isWish = already
        updateWishText()
    }

    private fun setFavorite(already: Boolean) {
        updateFavorite()
        isFavorite = already
        updateFavoriteText()
    }

    fun setupFavorite(already: Boolean, onF: OnFavorite, offF: OnFavorite) {
        onFavorite = onF
        offFavorite = offF
        isFavorite = already
        updateFavoriteText()
    }

    fun onData(detail: DetailInformation) {
        movieTitle.text = detail.title
        year.text = detail.year.split('-')[0]
        rate.text = detail.rate
        description.text = detail.description
        this.detail = detail
        Glide.with(this).load(detail.urlBackdrop.path()).into(backdrop)
        Glide.with(this).load(detail.urlPoster.path()).into(poster)

        favorite.setOnClickListener {
            setFavorite(!isFavorite)
        }

        wish.setOnClickListener {
            setWish(!isWish)
        }

        shimmerFrameLayout.gone()
        errorButton.gone()
        detailInformation.forEach { it.visible() }
    }

    fun onError() {
        shimmerFrameLayout.gone()
        detailInformation.forEach { it.gone() }
        errorButton.visible()
    }

    fun onErrorClickListener(onClickListener: () -> Unit) {
        errorButton.setOnClickListener { onClickListener.invoke() }
    }

    fun onLoading(loading: Boolean) {
        if(loading) {
            shimmerFrameLayout.visible()
            detailInformation.forEach { it.gone() }
            errorButton.gone()
        }
    }

    private fun updateWishText() {
        if(isWish) {
            wish.text = context.getString(R.string.wanting_watch)
        } else {
            wish.text = context.getString(R.string.want_watch)
        }
    }

    private fun updateWish() {
        if(isWish) {
            offWish.invoke(detail)
        } else {
            onWish.invoke(detail)
        }
    }

    private fun updateFavoriteText() {
        if(isFavorite) {
            favorite.text = context.getString(R.string.disfavor)
        } else {
            favorite.text = context.getString(R.string.favor)
        }
    }

    private fun updateFavorite() {
        if(isFavorite) {
            offFavorite.invoke(detail)
        } else {
            onFavorite.invoke(detail)
        }
    }
}