package com.kareem.moviekt.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.kareem.moviekt.R
import com.kareem.moviekt.adapter.movie.MovieCastAdapter
import com.kareem.moviekt.adapter.movie.VideoMovieAdapter
import com.kareem.moviekt.database.Favorite
import com.kareem.moviekt.database.FavoriteDatabase
import com.kareem.moviekt.model.*
import com.kareem.moviekt.network.popinterface
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityDetails : AppCompatActivity() {
    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    var maxLimit: Int = 995
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val baseURL = "https://image.tmdb.org/t/p/w780/"
    val service = retrofit.create(popinterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        txt_cast_detail.isVisible = false

        val id = intent.getStringExtra("id")!!.toInt()
        val type = intent.getStringExtra("type")

        val db: FavoriteDatabase by lazy {
            Room.databaseBuilder(this, FavoriteDatabase::class.java, "Fav.db")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }
        var fill = 0
        val imgFavorite = findViewById<ImageView>(R.id.img_favorite)
        val isFavorite = db.FavoriteDao().isFavorite(id.toString())
        if (isFavorite == null) {
            fill = 0
            imgFavorite.setImageResource(R.drawable.ic_favorite_border)
        } else {
            fill = 1
            imgFavorite.setImageResource(R.drawable.ic_favorite_fill)
        }

        imgFavorite.setOnClickListener {
            if (fill == 1) {
                imgFavorite.setImageResource(R.drawable.ic_favorite_border)
                db.FavoriteDao().delete(id.toString())
                Toast.makeText(this, "Remove from Favorite", Toast.LENGTH_SHORT).show()
                fill = 0
            } else {
                service.getmovies(id, api_key).enqueue(object : Callback<movie_search> {
                    override fun onFailure(call: Call<movie_search>, t: Throwable) {
                        Log.d("onFailure: ", t.toString())
                    }

                    override fun onResponse(
                        call: Call<movie_search>,
                        response: Response<movie_search>
                    ) {
                        val data = response.body()
                        if (response.isSuccessful) {
                            var obj = Favorite(
                                movie_id = id.toString(),
                                path = data!!.poster_path.toString()
                            )
                            db.FavoriteDao().insertRow(obj)
                            imgFavorite.setImageResource(R.drawable.ic_favorite_fill)
                            Toast.makeText(
                                this@ActivityDetails,
                                "Added to Favorite",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
            }
        }

        service.getmovies(id, api_key).enqueue(object : Callback<movie_search> {
            override fun onFailure(call: Call<movie_search>, t: Throwable) {
                Log.d("onFailure: ", t.toString())
            }

            override fun onResponse(call: Call<movie_search>, response: Response<movie_search>) {
                val data = response.body()
                if (data != null) {

                    Picasso.get()
                        .load(baseURL + data.poster_path)
                        .into(img_poster_path)

                    tv_release_date.text = "Release Date  " + data.release_date
                    tv_vote_average.text = data.vote_average + "/10"
                    tv_overview.text = data.overview
                }
            }

        })

        service.getcast(id, api_key).enqueue(object : Callback<moviecastresopnse> {
            override fun onFailure(call: Call<moviecastresopnse>, t: Throwable) {
                Log.d("onFailure: ", t.toString())
            }

            override fun onResponse(
                call: Call<moviecastresopnse>,
                response: Response<moviecastresopnse>
            ) {
                val data = response.body()
                val data1 = data?.cast
                txt_cast_detail.isVisible = true
                rv_cast_detail.layoutManager =
                    LinearLayoutManager(this@ActivityDetails, RecyclerView.HORIZONTAL, false)
                rv_cast_detail.adapter = data1?.let {
                    MovieCastAdapter(this@ActivityDetails, it, false)
                }
            }

        })

        service.getvideos(id, api_key).enqueue(object : Callback<videoresponse> {
            override fun onFailure(call: Call<videoresponse>, t: Throwable) {
                Log.d("onFailure: ", t.toString())
            }

            override fun onResponse(call: Call<videoresponse>, response: Response<videoresponse>) {
                val data = response.body()
                val data1 = data?.results
                rv_trailer_detail.layoutManager =
                    LinearLayoutManager(this@ActivityDetails, RecyclerView.HORIZONTAL, false)
                rv_trailer_detail.adapter = data1?.let {
                    VideoMovieAdapter(this@ActivityDetails, it, false)
                }
            }

        })
    }
}