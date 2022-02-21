package com.example.room_mellafesa_22

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.room_mellafesa_22.room.Constant
import com.example.room_mellafesa_22.room.Movie
import com.example.room_mellafesa_22.room.MovieDb
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddActivity : AppCompatActivity() {

    val db by lazy{ MovieDb(this) }
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setupView()
        setupListener()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_update.visibility = View.GONE

            }
            Constant.TYPE_READ -> {
                btn_save.visibility = View.GONE
                btn_update.visibility = View.GONE
                getMovies()
            }
            Constant.TYPE_UPDATE -> {
                btn_save.visibility = View.GONE
                getMovies()
            }
        }
    }

    fun setupListener(){
        btn_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.movieDao().addMovie(
                    Movie(0, et_title.text.toString(), et_description.text.toString())
                )

                finish()
            }
        }
        btn_update.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.movieDao().updateMovie(
                    Movie(movieId, et_title.text.toString(), et_description.text.toString())
                )
                finish()
            }

        }
    }

    fun getMovies(){
        movieId = intent.getIntExtra("movie_id", 0)
            CoroutineScope(Dispatchers.IO).launch {
               val movie = db.movieDao().getMovies(movieId).get(0)
                et_title.setText( movie.title )
                et_description.setText( movie.desc )

        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}

