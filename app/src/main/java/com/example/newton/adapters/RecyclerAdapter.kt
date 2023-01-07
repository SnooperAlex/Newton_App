package com.example.newton.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newton.R

class RecyclerAdapter (
    private var titles: List<String>,
    private var description: List<String>,
    private var images: List<String>,
    private var links: List<String>,
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(newsView: View): RecyclerView.ViewHolder(newsView){
        val newsTitle: TextView = newsView.findViewById(R.id.news_title)
        val newsDescription: TextView = newsView.findViewById(R.id.news_description)
        val newsImage: ImageView = newsView.findViewById(R.id.imageView)

        init {
            newsView.setOnClickListener{v: View ->
                val position: Int = adapterPosition

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(links[position])
                startActivity(itemView.context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.newsTitle.text = titles[position]
        holder.newsDescription.text = description[position]

    }


}