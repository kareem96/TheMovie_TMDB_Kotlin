package com.kareem.moviekt.ui.tv

import android.os.Bundle
import android.util.Log
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.adapter.tv.ViewAllAdapterTv
import com.kareem.moviekt.model.tv
import com.kareem.moviekt.model.tvresponse
import com.kareem.moviekt.network.popinterface
import kotlinx.android.synthetic.main.activity_all_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewAllTv : AppCompatActivity() {
    var language = "en_US"
    val api_key: String = "0e03d86efe00ea1a1e1dd7d2a4717ba1"
    var maxLimit: Int = 996
    val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var isSCrolling: Boolean = false
    var currentItems: Int = 0
    var totalItems: Int = 0
    var scrolledOutItems: Int = 0
    var currentPage: Int = 1
    var i = 0
    var count = 0
    lateinit var tvList: ArrayList<tv>
    lateinit var layoutManager: RecyclerView.LayoutManager
    private var gridLayoutManager: GridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_movie)

        val type = intent.getStringExtra("type")
        val service = retrofit.create(popinterface::class.java)

        fun toBeCalled() {
            if (type == "Popular") {
                service.getpopulartv(api_key, currentPage.toString())
                    .enqueue(object : Callback<tvresponse> {
                        override fun onFailure(call: Call<tvresponse>, t: Throwable) {
                            Log.d("onFailure: ", t.toString())
                        }

                        override fun onResponse(
                            call: Call<tvresponse>,
                            response: Response<tvresponse>
                        ) {
                            val data = response.body()
                            val data1 = data!!.results

                            progressBar_all_movie.isVisible =false

                            if (i==0){
                                tvList =data1
                                rv_all_movie.layoutManager = GridLayoutManager(this@ViewAllTv, 2)
                                rv_all_movie.adapter = ViewAllAdapterTv(this@ViewAllTv, tvList, false)
                            }else{
                                tvList.addAll(data1)
                                rv_all_movie.adapter!!.notifyDataSetChanged()
                            }
                            i++
                        }
                    })
            }else
                if (type == "airingtoday"){
                    service.getairingtodayall(api_key,currentPage.toString()).enqueue(object : Callback<tvresponse>{
                        override fun onFailure(call: Call<tvresponse>, t: Throwable) {
                            Log.d("onFailure: ", t.toString())
                        }

                        override fun onResponse(
                            call: Call<tvresponse>,
                            response: Response<tvresponse>
                        ) {
                            val data = response.body()
                            val data1=data!!.results
                            progressBar_all_movie.isVisible = false


                            if (i==0){
                                tvList =data1
                                rv_all_movie.layoutManager = GridLayoutManager(this@ViewAllTv, 2)
                                rv_all_movie.adapter = ViewAllAdapterTv(this@ViewAllTv, tvList, false)
                            }else{
                                tvList.addAll(data1)
                                rv_all_movie.adapter!!.notifyDataSetChanged()
                            }
                            i++
                        }
                    })
                }//
        }

        toBeCalled()

        rv_all_movie.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManager = rv_all_movie.layoutManager!!
                currentItems = layoutManager.childCount
                totalItems = layoutManager.itemCount
                when(layoutManager){
                    is GridLayoutManager -> gridLayoutManager = layoutManager as GridLayoutManager
                }
                scrolledOutItems =gridLayoutManager!!.findFirstVisibleItemPosition()

                if ((scrolledOutItems +currentItems == totalItems) && isSCrolling){
                    currentPage++
                    isSCrolling = false
                    toBeCalled()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isSCrolling = true
                }
            }
        })
    }
}