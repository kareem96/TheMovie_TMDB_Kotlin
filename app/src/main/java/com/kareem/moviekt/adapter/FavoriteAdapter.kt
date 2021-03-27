package com.kareem.moviekt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.model.movie_search
import com.kareem.moviekt.ui.movie.ActivityDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_favorite.view.*

class FavoriteAdapter(
    val context: Context,
    val namelist: ArrayList<movie_search>,
    val check: Boolean
) : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {
    val baseURL = "https://image.tmdb.org/t/p/w342/"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView=layoutInflater.inflate(R.layout.card_favorite,parent,false)
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
        if (check==false){
            val item = this.namelist
            holder.itemView.txt_card_favorite.text=item[position].original_title
            val target=item[position].poster_path
            Picasso
                .get()
                .load(baseURL+target)
                .into(holder.itemView.image_card_favorite)
            holder.itemView.card_favorite.setOnClickListener {
                val intent = Intent(context, ActivityDetails::class.java)
                intent.putExtra("id", item[position].id)
                intent.putExtra("type", "Movie")
                ContextCompat.startActivity(context,intent, null)
            }

        }
    }

}