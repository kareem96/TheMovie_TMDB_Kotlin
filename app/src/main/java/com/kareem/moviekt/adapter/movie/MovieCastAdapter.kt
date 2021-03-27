package com.kareem.moviekt.adapter.movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.model.moviecast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_cast.view.*

class MovieCastAdapter(val context: Context, val namelist: List<moviecast>, val check: Boolean) :
    RecyclerView.Adapter<MovieCastAdapter.MyViewHolder>() {
    val baseURL = "https://image.tmdb.org/t/p/w342/"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.card_cast, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (check == false)
            if (namelist != null) {
                return namelist.size
            }
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = this.namelist[position]
        if (item.name != null) {
            holder.itemView.name_cast.text = item.name
            val target = item.profile_path
            Picasso
                .get()
                .load(baseURL + target)
                .resize(140, 120)
                .into(holder.itemView.image_cast)
        } else {
            holder.itemView.name_cast.text = " "
        }

        holder.itemView.card_cast.setOnClickListener {

        }

    }

}