package com.gadidev.acar.adapter

import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gadidev.acar.R
import com.gadidev.acar.model.Gif
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.base.PlutoViewHolder

class GifAdapter(items: MutableList<Gif>
) : PlutoAdapter<Gif, GifAdapter.ViewHolder>(items) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.item_slide_card)
    }

    class ViewHolder(parent: ViewGroup, itemLayoutId: Int) : PlutoViewHolder<Gif>(parent, itemLayoutId) {
        internal var ivPoster: ImageView = getView(R.id.iv_gif)


        override fun set(item: Gif, pos: Int) {
                    Glide.with(context)
            .load(item.url)
            .apply(RequestOptions().override(320, 220))
            .into(ivPoster)
//            Glide.with(context).asGif().load(item.url).into(ivPoster)

        }
    }
}