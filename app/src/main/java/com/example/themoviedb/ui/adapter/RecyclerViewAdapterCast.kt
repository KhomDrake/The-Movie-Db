package com.example.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedb.R
import com.example.themoviedb.extension.path
import com.example.themoviedb.models.network.Cast
import de.hdodenhof.circleimageview.CircleImageView

class RecyclerViewAdapterCast(
    private val images: List<Cast>
) : RecyclerView.Adapter<RecyclerViewAdapterCast.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = images.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.cast_name)
        private val image = itemView.findViewById<CircleImageView>(R.id.cast_image)

        fun bind(cast: Cast) {
            val path = cast.profilePath.path()
            Glide.with(itemView).load(path).into(image)
            name.text = cast.name
        }
    }
}