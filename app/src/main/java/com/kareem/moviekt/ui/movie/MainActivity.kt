package com.kareem.moviekt.ui.movie

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kareem.moviekt.R
import com.kareem.moviekt.adapter.movie.MovieAdapter
import com.kareem.moviekt.adapter.movie.UpComingMovieAdapter
import com.kareem.moviekt.model.movieresponse
import com.kareem.moviekt.network.popinterface
import com.kareem.moviekt.ui.favorite.FavoriteActivity
import com.kareem.moviekt.ui.tv.TvActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    val maxLimit: Int = 996
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(popinterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txt_now_playing.isVisible = false
        more_now_playing.isVisible = false
        txt_up_coming.isVisible = false
        more_up_coming.isVisible = false


        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarMovie)
        setSupportActionBar(toolbar)
        val naview = findViewById<View>(R.id.nav_movie) as BottomNavigationView
        val menu = naview.menu

        val menuItem = menu.getItem(0)
        menuItem.isChecked = true

        naview.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movies -> {
                    startActivity(Intent(application, MainActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tv -> {
                    startActivity(Intent(application, TvActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    startActivity(Intent(application, FavoriteActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener true
            }
        }
        start()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        var manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchitem = menu?.findItem(R.id.searchid)
        var searchview = searchitem?.actionView as androidx.appcompat.widget.SearchView
        searchview.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchview.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchview.queryHint = "Search.."

                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra("text", query)
                intent.putExtra("type", "movie")
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchid -> {
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    private fun start() {

        more_now_playing.setOnClickListener {
            val intent = Intent(this, ViewAllMovie::class.java)
            intent.putExtra("type", "Nowplaying")
            startActivity(intent, null)
        }
        more_up_coming.setOnClickListener {
            val intent = Intent(this, ViewAllMovie::class.java)
            intent.putExtra("type", "Upcoming")
            startActivity(intent, null)
        }


        service.getNowplaying(api_key, "1").enqueue(object : Callback<movieresponse> {
            override fun onFailure(call: Call<movieresponse>, t: Throwable) {
                Log.d("onFailure: ", t.toString())
            }

            override fun onResponse(call: Call<movieresponse>, response: Response<movieresponse>) {
                val data = response.body()
                val data1 = data?.results

                txt_now_playing.isVisible = true
                more_now_playing.isVisible = true
                progressBar_movie.isVisible = false

                rv_now_playing_movie.layoutManager =
                    LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
                rv_now_playing_movie.adapter = data1?.let {
                    MovieAdapter(this@MainActivity, it, false)
                }
            }
        })

        service.getUpcoming(api_key, "1").enqueue(object : Callback<movieresponse> {
            override fun onFailure(call: Call<movieresponse>, t: Throwable) {
                Log.d("MoviesDagger", t.toString())
            }

            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<movieresponse>, response: Response<movieresponse>) {
                val data = response.body()
                val data1 = data?.results
                txt_up_coming.isVisible = true
                more_up_coming.isVisible = true
                progressBar_movie.isVisible = false

                rv_up_coming_movie.layoutManager =
                    GridLayoutManager(this@MainActivity, 2, RecyclerView.VERTICAL, false)
                rv_up_coming_movie.adapter = data1?.let {
                    UpComingMovieAdapter(
                        this@MainActivity,
                        it,
                        false
                    )
                }

            }
        })
    }
}//class

