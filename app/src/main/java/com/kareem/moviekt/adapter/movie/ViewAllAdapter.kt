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
import kotlinx.android.synthetic.main.card_all_layout.view.*

class ViewAllAdapter(
    val context: Context,
    val nameList: List<movie>,
    val check: Boolean
) : RecyclerView.Adapter<ViewAllAdapter.MyViewHolder>() {

    val baseURL = "https://image.tmdb.org/t/p/w342/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.card_all_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (check == false)
            if (nameList != null) {
                return nameList.size
            }
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = this.nameList[position]
        holder.itemView.txt_card_all.text = item.original_title
        val target = item.poster_path
        Picasso
            .get()
            .load(baseURL + target)
            .resize(180, 210)
            .into(holder.itemView.image_card_all)

        holder.itemView.card_all.setOnClickListener {
            val intent = Intent(context, ActivityDetails::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("type", "Movie")
            ContextCompat.startActivity(context, intent, null)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}