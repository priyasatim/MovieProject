package com.example.mvvm.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.models.MovieSearchList
import com.example.mvvm.databinding.RecycleViewMovieBinding

class MovieAdapter (
    private val movieList :List<MovieSearchList>,
    private val listener: RecycleviewClickListener
)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    inner class MovieViewHolder(val recycleviewMovieBinding :RecycleViewMovieBinding):RecyclerView.ViewHolder(recycleviewMovieBinding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MovieViewHolder(DataBindingUtil.inflate<RecycleViewMovieBinding>(LayoutInflater.from(parent.context), R.layout.recycle_view_movie,parent,false))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.recycleviewMovieBinding.movies = movieList[position]

        holder.recycleviewMovieBinding.root.setOnClickListener {
            listener.onRecycleViewItemClick(holder.recycleviewMovieBinding.constraintLayout,movieList[position],position)
        }
    }

    override fun getItemCount() = movieList.size
}