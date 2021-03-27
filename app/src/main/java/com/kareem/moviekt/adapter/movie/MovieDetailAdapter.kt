package com.kareem.moviekt.adapter.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.model.movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.view.*

class MovieDetailAdapter(
    val context: Context,
    val nameList: List<movie>,
    val check: Boolean
) : RecyclerView.Adapter<MovieDetailAdapter.MyViewHolder>() {
    val baseURL = "https://image.tmdb.org/t/p/w342/"

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView=layoutInflater.inflate(R.layout.activity_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (!check)
            return nameList.size
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = this.nameList[position]
        val target=item.poster_path

    }

}