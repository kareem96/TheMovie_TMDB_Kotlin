package com.kareem.moviekt.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kareem.moviekt.R
import com.kareem.moviekt.adapter.FavoriteAdapter
import com.kareem.moviekt.database.FavoriteDatabase
import com.kareem.moviekt.model.movie_search
import com.kareem.moviekt.network.popinterface
import com.kareem.moviekt.ui.movie.MainActivity
import com.kareem.moviekt.ui.tv.TvActivity
import kotlinx.android.synthetic.main.activity_favorite.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoriteActivity : AppCompatActivity() {
    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    var maxLimit: Int = 996
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val baseURL = "https://image.tmdb.org/t/p/w780/"
    val service = retrofit.create(popinterface::class.java)
    var favList: ArrayList<movie_search> = arrayListOf<movie_search>()
    var check: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val naview = findViewById<View>(R.id.nav) as BottomNavigationView
        var menu = naview.menu
        var menuItem = menu.getItem(2)
        menuItem.isChecked = true

        naview.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movies -> {
                    val intent1 = Intent(this, MainActivity::class.java)
                    startActivity(intent1)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tv -> {
                    val intent2 = Intent(this, TvActivity::class.java)
                    startActivity(intent2)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener true

            }

        }
        val db: FavoriteDatabase by lazy {
            Room.databaseBuilder(this, FavoriteDatabase::class.java, "Fav.db")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build()
        }
        var a = db.FavoriteDao().getAllFav()
        Log.d("onCreate: ", a.size.toString())

        for (i in 0 .. a.lastIndex){
            Log.d("onCreate: ", "LOOP")

            service.getmovies(a[i].movie_id.toInt(), api_key).enqueue(object : Callback<movie_search>{
                override fun onFailure(call: Call<movie_search>, t: Throwable) {
                    Log.d("onFailure: ", t.toString())
                }

                override fun onResponse(
                    call: Call<movie_search>,
                    response: Response<movie_search>
                ) {
                    val data=response.body()
                    Log.d("onResponse: ", data!!.original_title)
                    favList.add(data)
                    Log.d( "added", " ")

                    if (i == a.lastIndex){
                        Log.d( "onResponse: ", "${favList.size}")
                        if (favList.size !== 0){
                            check = false
                        }
                        rvfav.layoutManager = GridLayoutManager(this@FavoriteActivity, 2, RecyclerView.VERTICAL, false)
                        rvfav.adapter = FavoriteAdapter(this@FavoriteActivity, favList, check)
                        rvfav.adapter!!.notifyDataSetChanged()
                    }
                }

            })
        }
        favList.clear()

    }

    override fun onBackPressed() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finishAffinity()
    }
}