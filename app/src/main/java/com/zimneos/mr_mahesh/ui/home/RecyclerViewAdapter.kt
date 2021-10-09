package com.zimneos.mr_mahesh.ui.home


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zimneos.mr_mahesh.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.layout_main_screen_recylerview_list.view.*


class RecyclerViewAdapter(
    options: FirebaseRecyclerOptions<Holding?>,
    var clickListener: OnItemListener) :
    FirebaseRecyclerAdapter<Holding?, RecyclerViewAdapter.MyViewHolder?>(options) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, holding: Holding) {
        holder.movieName.text = holding.title
        Glide.with(holder.moviePoster.context).load(holding.poster)
            .error(R.color.light_dark).into(
                holder.moviePoster
            )
        holder.movieDirector.text = "By ${holding.director}"
        holder.movieYear.text = holding.year
        Glide.with(holder.movieRating.context).load(holding.rating)
            .error(R.color.light_dark).into(
                holder.movieRating
            )
        ViewCompat.setTransitionName(holder.moviePoster, holder.adapterPosition.toString())
        holder.initialize(holding, clickListener)

    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var movieName: TextView = itemView.title
        var moviePoster: ShapeableImageView = itemView.poster
        var movieDirector: TextView = itemView.director
        var movieYear: TextView = itemView.year
        var movieRating: ImageView = itemView.rating

        fun initialize(holderList: Holding, action: OnItemListener) {
            itemView.setOnClickListener {
                action.onItemClick(holderList, adapterPosition,moviePoster,movieName)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_main_screen_recylerview_list, parent, false)
        return MyViewHolder(view)
    }

    interface OnItemListener {
        fun onItemClick(
            listener: Holding,
            position: Int,
            poster: ShapeableImageView,
            title: TextView
        )
    }
}


