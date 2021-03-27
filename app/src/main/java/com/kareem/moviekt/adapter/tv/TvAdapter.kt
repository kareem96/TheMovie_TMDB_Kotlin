package com.kareem.moviekt.adapter.tv

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.model.tv
import com.kareem.moviekt.ui.movie.ActivityDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_horizontal.view.*

class TvAdapter(
    val context: Context,
    val namelist: List<tv>,
    val check: Boolean
) : RecyclerView.Adapter<TvAdapter.MyViewHolder>() {
    val baseURL = "https://image.tmdb.org/t/p/w1280/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var li = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(R.layout.layout_horizontal, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (!check)
            return namelist.size
        return 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item1 = this.namelist[position]
        holder.itemView.txt_horizontal.text = item1.original_name
        val target = item1.backdrop_path
        Picasso.get().load(baseURL + target).resize(300, 180).into(holder.itemView.image_horizontal)

        holder.itemView.card_horizontal.setOnClickListener {
            val intent= Intent(context, ActivityDetails::class.java)
            intent.putExtra("id",item1.id)
            intent.putExtra("type","Tv")
            ContextCompat.startActivity(context, intent, null)

        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
