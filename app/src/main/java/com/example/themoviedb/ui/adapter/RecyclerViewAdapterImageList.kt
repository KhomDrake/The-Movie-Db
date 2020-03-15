package com.example.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.extension.gone
import com.example.themoviedb.extension.visible
import com.example.themoviedb.models.application.ImageInfo
import com.example.themoviedb.models.application.createUrl

class RecyclerViewAdapterImageList(
    private val images: List<ImageInfo>,
    private val onClickMovie: (ImageInfo) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapterImageList.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_info, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = images.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position], onClickMovie)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.imageView)
        private val name = itemView.findViewById<TextView>(R.id.image_name)

        fun bind(imageInfo: ImageInfo, onClickMovie: (ImageInfo) -> Unit) {
            Glide.with(itemView).load(imageInfo.createUrl()).into(image)
            image.setOnClickListener { onClickMovie.invoke(imageInfo) }
            imageInfo.title?.apply {
                name.text = this
                name.visible()
            } ?: also {
                name.gone()
            }
        }
    }
}