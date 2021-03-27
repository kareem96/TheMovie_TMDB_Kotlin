package com.kareem.moviekt.adapter.movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.model.movie
import com.kareem.moviekt.ui.movie.ActivityDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_all_layout.view.*
import kotlinx.android.synthetic.main.layout_horizontal.view.*

class MovieAdapter(val context: Context, val namelist: List<movie>, val check: Boolean) :
    RecyclerView.Adapter<MovieAdapter.myviewholder>() {

    val baseURL = "https://image.tmdb.org/t/p/w342/"

    override fun getItemCount(): Int {
        if (check == false)
            if (namelist != null) {
                return namelist.size

            }

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {

        var li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.layout_horizontal, parent, false)
        return myviewholder(itemView)

    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {

        val item1 = this.namelist[position]
        holder.itemView.txt_horizontal.text = item1.original_title
        val target = item1.poster_path

        Picasso
            .get()
            .load(baseURL + target)
            .resize(300, 180)
            .into(holder.itemView.image_horizontal)

        holder.itemView.card_horizontal.setOnClickListener {

            val intent = Intent(context, ActivityDetails::class.java)
            intent.putExtra("id", item1.id)
            intent.putExtra("type", "Movie")
            startActivity(context, intent, null)
        }

    }

    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView)

}