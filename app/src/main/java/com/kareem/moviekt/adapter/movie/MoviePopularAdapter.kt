package com.kareem.moviekt.adapter.movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.model.movie
import com.kareem.moviekt.ui.movie.ActivityDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_image_list.view.*

class MoviePopularAdapter(val context: Context, val namelist: List<movie>, val check: Boolean) :
    RecyclerView.Adapter<MoviePopularAdapter.MyViewHolder>() {
    val baseURL = "https://image.tmdb.org/t/p/w342/"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.layout_image_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (!check)
            return namelist.size
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item1 = this.namelist[position]
        holder.itemView.txt_layout_list.text = item1.original_title
        val target = item1.poster_path

        Picasso.get().load(baseURL + target).resize(300, 300).into(holder.itemView.image_list)

        holder.itemView.layout_image_list.setOnClickListener {
            val intent = Intent(context, ActivityDetails::class.java)
            intent.putExtra("id", item1.id)
            intent.putExtra("type", "Movie")
            ContextCompat.startActivity(context, intent, null)
        }

    }
}
