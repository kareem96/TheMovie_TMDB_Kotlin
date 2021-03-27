package com.kareem.moviekt.ui.tv

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kareem.moviekt.R
import com.kareem.moviekt.adapter.tv.TvAdapter
import com.kareem.moviekt.model.tvresponse
import com.kareem.moviekt.network.popinterface
import com.kareem.moviekt.ui.favorite.FavoriteActivity
import com.kareem.moviekt.ui.movie.MainActivity
import com.kareem.moviekt.ui.movie.SearchActivity
import kotlinx.android.synthetic.main.activity_tv.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TvActivity : AppCompatActivity() {

    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    val maxLimit: Int = 996
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(popinterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv)

        txt_airing_today.isVisible = false
        more_airing_today.isVisible = false

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbartv)
        setSupportActionBar(toolbar)
        val naview = findViewById<BottomNavigationView>(R.id.navtv)
        val menu = naview.menu
        val menuItem = menu.getItem(1)
        menuItem.isChecked = true

        naview.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.movies -> {
                    startActivity(Intent(application, MainActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.tv -> {
//                    val intent2 = Intent(this, TvActivity::class.java)
//                    startActivity(intent2)
                    startActivity(Intent(application, TvActivity::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    val intent4 = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent4)
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

                val intent = Intent(this@TvActivity, SearchActivity::class.java)
                intent.putExtra("text", query)
                intent.putExtra("type", "tv")
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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun start() {
        more_airing_today.setOnClickListener {
            val intent = Intent(this, ViewAllTv::class.java)
            intent.putExtra("type", "Popular")
            startActivity(intent, null)
        }

        service.getairingtodayall(api_key, "1").enqueue(object : Callback<tvresponse> {
            override fun onFailure(call: Call<tvresponse>, t: Throwable) {
                Log.d("MoviesDagger", t.toString())
            }


            override fun onResponse(call: Call<tvresponse>, response: Response<tvresponse>) {

                val data = response.body()
                val data1 = data?.results
                txt_airing_today.isVisible = true
                more_airing_today.isVisible = true
                progressBartv.isVisible = false


                rv_airing_today.layoutManager =
                    LinearLayoutManager(this@TvActivity, RecyclerView.HORIZONTAL, false)
                rv_airing_today.adapter = data1?.let {
                    TvAdapter(
                        this@TvActivity,
                        it,
                        false
                    )
                }


            }
        })
    }
}//class