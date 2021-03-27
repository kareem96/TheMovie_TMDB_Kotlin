package com.kareem.moviekt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.model.Search
import com.kareem.moviekt.ui.movie.ActivityDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.seachlayout.view.*

class SearchAdapter(val context: Context, val namelist:List<Search>, val check:Boolean): RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    val baseURL = "https://image.tmdb.org/t/p/w780/"


    override fun getItemCount(): Int {
        if(check==false)
            if (namelist != null) {
                return namelist.size

            }

        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var li=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView=li.inflate(R.layout.seachlayout,parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item1= this.namelist[position]


        holder.itemView.ltView.text = item1.original_title

        var target : String

        target = item1.poster_path

        Picasso.get().load(baseURL + target).into(holder.itemView.liView)




        holder.itemView.card_search.setOnClickListener {

            val intent= Intent(context, ActivityDetails::class.java)
            intent.putExtra("id",item1.id)
            intent.putExtra("type","movie")
            ContextCompat.startActivity(context, intent, null)
        }
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
}