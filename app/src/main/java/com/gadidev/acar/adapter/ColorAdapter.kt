package com.gadidev.acar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gadidev.acar.R
import com.gadidev.acar.model.Card

class ColorAdapter(val listColor: ArrayList<Card>) : RecyclerView.Adapter<ColorAdapter.ListViewHolder> () {

    override fun getItemCount(): Int {
        return listColor.size
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Card)
    }
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imgColor : ImageView = itemView.findViewById(R.id.imageViewColor)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_color, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val main = listColor[position]
        Glide.with(holder.itemView.context)
                .load(main.image)
                .apply(RequestOptions())
                .into(holder.imgColor)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listColor[holder.adapterPosition])
        }
    }
}