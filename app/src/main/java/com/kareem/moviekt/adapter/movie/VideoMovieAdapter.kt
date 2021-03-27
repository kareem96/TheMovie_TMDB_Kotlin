package com.kareem.moviekt.adapter.movie

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kareem.moviekt.R
import com.kareem.moviekt.model.video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_video.view.*

class VideoMovieAdapter(val context: Context, val namelist: List<video>, val check: Boolean) :
    RecyclerView.Adapter<VideoMovieAdapter.MyViewHolder>() {
    var baseURL = "https://www.youtube.com/watch?v="

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.card_video, parent, false)
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
        val extraid = item.key
        val a = item.id
        val img_url = "http://img.youtube.com/vi/$extraid/0.jpg"
        Log.d("onBindViewHolder: ", item.key)
        Picasso
            .get()
            .load(img_url)
            .centerCrop()
            .resize(250, 200)
            .into(holder.itemView.videoimage)
    }

}
