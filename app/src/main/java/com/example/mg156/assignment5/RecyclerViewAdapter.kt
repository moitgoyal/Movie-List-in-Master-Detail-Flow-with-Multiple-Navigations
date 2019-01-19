package com.example.mg156.assignment5

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class RecyclerViewAdapter() : RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder>() {

    var myListener : MyItemClickListener ? = null
    var lastPosition = -1

    interface MyItemClickListener {
        fun onItemClickedFromAdapter (movie : MovieDataClass )
        fun onItemLongClickedFromAdapter ( position : Int )
        fun onOverFlowMenuClick(view: View, position: Int)
    }

    fun setMyItemClickListener ( listener : MyItemClickListener ) {
        this.myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val v: View
        when (viewType) {
            1 -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
            2 -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_row2, parent, false)
            3 -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_row3, parent, false)
            else -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        }
        return MovieViewHolder(v)
    }

    override fun getItemCount () : Int {
        return movieList.size
    }

    override fun onBindViewHolder ( holder : MovieViewHolder , position : Int ) {
        val movie = movieList[position]
        holder.moviePoster.setImageResource(posterTable.get(movie.title)!!)
        holder.movieTitle.text = movie.title
        holder.movieOverview.text = movie.overview
        holder.movieSelect.setChecked(movie.selection as Boolean)

        setAnimation(holder.moviePoster , position )

    }

    fun setAnimation ( view : View , position : Int ) {
        if ( position != lastPosition ) {
            var animation =  AnimationUtils.loadAnimation( view.context, android.R.anim.slide_in_left);
            animation.setDuration(1000);
            view.startAnimation(animation) ;
            lastPosition = position
        }
    }


    override fun getItemViewType ( position : Int ): Int {
        return if (position < 3) {
            1
        } else if (position >= itemCount - 3) {
            3
        } else
            2
    }

    fun SearchData(query: String): Int {
        for (i in movieList.indices) {
            val movie = movieList[i]
            if (movie.title.toString().toLowerCase().contains(query.toLowerCase()) == true)
                return i
            if (movie.overview.toString().toLowerCase().contains(query.toLowerCase()) == true)
                return i
        }
        return -1
    }

    fun sortItemsByTitle() {
        movieList.sortWith(compareBy({it.title}))
    }

    fun sortItemsByRating() {
        movieList.sortWith(compareByDescending({it.vote_average}))
    }

    inner class MovieViewHolder ( view : View ) : RecyclerView.ViewHolder ( view ) {
        val moviePoster = view.findViewById <ImageView>(R.id.item_image )
        val movieTitle = view.findViewById <TextView>(R.id.item_title )
        val movieOverview = view.findViewById <TextView>(R.id.item_overview )
        val movieSelect = view.findViewById <CheckBox>(R.id.item_checkBox )
        val movie_overflow_image = view.findViewById <ImageView>(R.id.item_overflow_image)
        init {
            view.setOnClickListener(View.OnClickListener { v ->
                if (myListener != null) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        myListener!!.onItemClickedFromAdapter(movieList[adapterPosition])
                    }
                }
            })

            view.setOnLongClickListener(View.OnLongClickListener { v ->
                if (myListener != null) {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        myListener!!.onItemLongClickedFromAdapter(adapterPosition)
                    }
                }
                true
            })

            movie_overflow_image.setOnClickListener(View.OnClickListener { v ->
                myListener!!.onOverFlowMenuClick(v, adapterPosition) })
        }
    }
}