package com.example.room_mellafesa_22

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room_mellafesa_22.room.Movie
import kotlinx.android.synthetic.main.list_movie.view.*

class MovieAdapter(private val movies: ArrayList<Movie>, var listener: OnAdapterListener) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_movie, parent, false)
        )
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie  = movies[position]
        holder.view.text_title.text = movie.title
        holder.view.text_title.setOnClickListener{
            listener.onClick(movie)
        }
        holder.view.icon_edit.setOnClickListener{
            listener.onUpdate(movie)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.onDelete(movie)
        }
    }



    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Movie>){
        movies.clear()
        movies.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(movies: Movie)
        fun onUpdate(movies: Movie)
        fun onDelete(movies: Movie)
    }
}