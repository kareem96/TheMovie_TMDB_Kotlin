package com.kareem.moviekt.ui.movie

import android.os.Bundle
import android.util.Log
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.adapter.movie.ViewAllAdapter
import com.kareem.moviekt.model.movie
import com.kareem.moviekt.model.movieresponse
import com.kareem.moviekt.network.popinterface
import kotlinx.android.synthetic.main.activity_all_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewAllMovie : AppCompatActivity() {
    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    var maxLimit: Int = 996
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var isScrolling: Boolean = false
    var currentItems: Int = 0
    var totalItems: Int = 0
    var scrolledOutItems: Int = 0
    var currentPage: Int = 1
    var i = 0
    var count = 0
    lateinit var commonList: ArrayList<movie>
    lateinit var layoutManager: RecyclerView.LayoutManager
    private var gridLayoutManager: GridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_movie)

        val type = intent.getStringExtra("type")
        val service = retrofit.create(popinterface::class.java)

        fun toBeCalled() {

            if (type == "Popular") {


                service.getPopular(api_key, currentPage.toString())
                    .enqueue(object : Callback<movieresponse> {
                        override fun onFailure(call: Call<movieresponse>, t: Throwable) {
                            Log.d("MoviesDagger", t.toString())
                        }


                        override fun onResponse(
                            call: Call<movieresponse>,
                            response: Response<movieresponse>
                        ) {

                            val data = response.body()
                            val data1 = data!!.results

                            progressBar_all_movie.isVisible = false

                            //  rView.layoutManager =
                            //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)
                            if (i == 0) {
                                commonList = data1
                                rv_all_movie.layoutManager =
                                    GridLayoutManager(this@ViewAllMovie, 2)
                                rv_all_movie.adapter = ViewAllAdapter(
                                    this@ViewAllMovie,
                                    commonList,
                                    false
                                )
                            } else {
                                commonList.addAll(data1)
                                rv_all_movie.adapter!!.notifyDataSetChanged()

                            }
                            i++


                        }
                    })
            } else
                if (type == "Nowplaying") {


                    service.getNowplaying(api_key, currentPage.toString())
                        .enqueue(object : Callback<movieresponse> {
                            override fun onFailure(call: Call<movieresponse>, t: Throwable) {
                                Log.d("MoviesDagger", t.toString())
                            }


                            override fun onResponse(
                                call: Call<movieresponse>,
                                response: Response<movieresponse>
                            ) {

                                val data = response.body()
                                val data1 = data!!.results
                                progressBar_all_movie.isVisible = false


                                //  rView.layoutManager =
                                //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                                if (i == 0) {
                                    commonList = data1
                                    rv_all_movie.layoutManager =
                                        GridLayoutManager(this@ViewAllMovie, 2)
                                    rv_all_movie.adapter = ViewAllAdapter(
                                        this@ViewAllMovie,
                                        commonList,
                                        false
                                    )
                                } else {
                                    commonList.addAll(data1)
                                    rv_all_movie.adapter!!.notifyDataSetChanged()

                                }
                                i++


                            }
                        })
                } else
                    if (type == "Toprated") {


                        service.getToprated(api_key, currentPage.toString())
                            .enqueue(object : Callback<movieresponse> {
                                override fun onFailure(call: Call<movieresponse>, t: Throwable) {
                                    Log.d("MoviesDagger", t.toString())
                                }


                                override fun onResponse(
                                    call: Call<movieresponse>,
                                    response: Response<movieresponse>
                                ) {

                                    val data = response.body()
                                    val data1 = data!!.results
                                    progressBar_all_movie.isVisible = false


                                    //  rView.layoutManager =
                                    //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                                    if (i == 0) {
                                        commonList = data1
                                        rv_all_movie.layoutManager =
                                            GridLayoutManager(this@ViewAllMovie, 2)
                                        rv_all_movie.adapter = ViewAllAdapter(
                                            this@ViewAllMovie,
                                            commonList,
                                            false
                                        )
                                    } else {
                                        commonList.addAll(data1)
                                        rv_all_movie.adapter!!.notifyDataSetChanged()

                                    }
                                    i++


                                }
                            })
                    } else
                        if (type == "Upcoming") {


                            service.getUpcoming(api_key, currentPage.toString())
                                .enqueue(object : Callback<movieresponse> {
                                    override fun onFailure(
                                        call: Call<movieresponse>,
                                        t: Throwable
                                    ) {
                                        Log.d("MoviesDagger", t.toString())
                                    }


                                    override fun onResponse(
                                        call: Call<movieresponse>,
                                        response: Response<movieresponse>
                                    ) {

                                        val data = response.body()
                                        val data1 = data!!.results
                                        progressBar_all_movie.isVisible = false

                                        //  rView.layoutManager =
                                        //     GridLayoutManager(this@MainActivity,2,RecyclerView.VERTICAL,false)

                                        if (i == 0) {
                                            commonList = data1
                                            rv_all_movie.layoutManager =
                                                GridLayoutManager(this@ViewAllMovie, 2)
                                            rv_all_movie.adapter = ViewAllAdapter(
                                                this@ViewAllMovie,
                                                commonList,
                                                false
                                            )
                                        } else {
                                            commonList.addAll(data1)
                                            rv_all_movie.adapter!!.notifyDataSetChanged()

                                        }
                                        i++


                                    }
                                })
                        }

        }
        toBeCalled()

        rv_all_movie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManager = rv_all_movie.layoutManager!!
                currentItems = layoutManager.childCount
                totalItems = layoutManager.itemCount
                when (layoutManager) {
                    is GridLayoutManager -> gridLayoutManager = layoutManager as GridLayoutManager
                }
                scrolledOutItems = gridLayoutManager!!.findFirstVisibleItemPosition()

                if ((scrolledOutItems + currentItems == totalItems) && isScrolling) {
                    currentPage++
                    isScrolling = false
                    toBeCalled()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
        })
    }

}