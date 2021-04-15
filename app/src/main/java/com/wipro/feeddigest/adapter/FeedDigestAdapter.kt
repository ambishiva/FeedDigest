package com.wipro.feeddigest.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.wipro.feeddigest.model.FeedDigest
import com.bumptech.glide.request.target.Target
import com.wipro.feeddigest.databinding.FeedDigestItemBinding
import javax.sql.DataSource

class FeedDigestAdapter() : RecyclerView.Adapter<FeedDigestAdapter.FeedDigestViewHolder>() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var feedDigestList: List<FeedDigest> = ArrayList()

    class FeedDigestViewHolder(val binding: FeedDigestItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    /* Inside the onCreateViewHolder inflate the view of FeedItemBinding
     and return new ViewHolder object containing this layout*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedDigestViewHolder {
        val binding = FeedDigestItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return FeedDigestViewHolder(binding)
    }


    override fun getItemCount(): Int = feedDigestList.size

    // Bind the items with each item of the list feed digest which than will be
    // shown in recycler view
    override fun onBindViewHolder(holder: FeedDigestViewHolder, position: Int) {
        with(holder) {
            with(feedDigestList[position]) {
                binding.feedDigestTitle.text = title
                binding.feedDigestDescription.text = description
                Glide.with(holder.itemView.context)
                    .load(this.imageHref)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            //Hide the image view if it is ready to load
                            binding.feedDigestImage.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            //Show the image view if it is ready to load
                            binding.feedDigestImage.visibility = View.VISIBLE
                            return false
                        }
                    })
                    .into(binding.feedDigestImage)

                // Hide the description is empy from backend
                if (description.isNullOrBlank()) binding.feedDigestDescription.visibility =
                    View.GONE
                else
                    binding.feedDigestDescription.visibility = View.VISIBLE

                // Hide the title is empy from backend
                if (title.isNullOrBlank()) binding.feedDigestTitle.visibility = View.GONE
                else
                    binding.feedDigestTitle.visibility = View.VISIBLE


                //Hide the item view if all the data from cloud is empty
                if (title.isNullOrBlank() && description.isNullOrBlank() && imageHref.isNullOrBlank()) {
                    holder.itemView.visibility = View.GONE
                    holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
                } else {
                    holder.itemView.visibility = View.VISIBLE
                    holder.itemView.layoutParams = RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }
        }
    }

    fun setFeedDigest(feedDigestList: List<FeedDigest>?) {
        feedDigestList?.let {
            this.feedDigestList = feedDigestList
            notifyDataSetChanged()
        }
    }
}