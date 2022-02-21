package com.example.room_mellafesa_22

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_mellafesa_22.room.Constant
import com.example.room_mellafesa_22.room.Movie
import com.example.room_mellafesa_22.room.MovieDb
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class MainActivity : Activity() {
    val db by lazy{ MovieDb(this) }
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        loadMovie()
    }


    fun loadMovie(){
        CoroutineScope(Dispatchers.IO).launch {
            val movies = db.movieDao().getMovies()
            Log.d("MainActivity", "dbResponse: $movies")
            withContext(Dispatchers.Main){
                movieAdapter.setData( movies )
            }
        }
    }

    fun setupListener(){
        add_movie.setOnClickListener{
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(movieId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id", movieId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView(){
        movieAdapter = MovieAdapter(arrayListOf(), object : MovieAdapter.OnAdapterListener {

            override fun onClick(movie: Movie){
                //read detail note
                intentEdit(movie.id, Constant.TYPE_READ)
            }

            override fun onUpdate(movie: Movie){
                intentEdit(movie.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(movies: Movie) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.movieDao().deleteMovie(movies)
                    loadMovie()
                }
            }



        })
        rv_movie.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = movieAdapter
        }
    }

    private fun MovieAdapter(
        movies: ArrayList<Movie>,
        onAdapterListener: MovieAdapter.OnAdapterListener
    ): MovieAdapter {
        TODO("Not yet implemented")
    }

}


