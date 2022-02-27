package com.goldmansanch.ui.favourite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.goldmansanch.data.APODItem
import com.goldmansanch.util.Constants
import com.goldmansanch.util.StringUtil
import com.golmansanch.R
import com.golmansanch.databinding.ApodListItemBinding

/**
 * Created by Abhishek on 27,February,2022 For GolmanSanch
 *
 */
class APODListAdapter(private var data: MutableList<APODItem> = mutableListOf(),private val favClick: (apodItem: APODItem) -> Unit) :
    RecyclerView.Adapter<APODListAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.apod_list_item, parent, false)
        return ViewHolder(view)
    }

    fun setData(newData: List<APODItem>?)
    {
        newData?.run {
            data.clear()
            data.addAll(newData)
            notifyItemChanged(0, data.size)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val apod = data[position]
        apod.isFav = true
        holder.itemBinding.data = apod
        loadImage(apod.url,apod.mediaType, holder.itemBinding.imageThumbnail)
        holder.itemView.setOnClickListener {
            favClick.invoke(apod)
        }

    }

    override fun getItemCount(): Int
    {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var itemBinding = ApodListItemBinding.bind(itemView)
    }

    private fun loadImage(
        url: String?,
        mediaType: String?,
        imageView: AppCompatImageView
    )
    {
        val urlToLoad = if (mediaType == Constants.MEDIA_TYPE_VIDEO)
        {
            StringUtil.getYoutubeVideoThumbnailURL(url)
        } else
        {
            url
        }
        Glide.with(imageView.context).load(urlToLoad)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder_error).centerInside()
            )
            .into(imageView)
        imageView.adjustViewBounds= true
    }


}