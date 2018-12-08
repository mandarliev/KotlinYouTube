package com.example.android.kotlinyoutube

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*

/*
Create the adapter
We need to subclass it as a recyclerview
It requires a special viewholder class
We then need to provide a customview holder class, which we will subclass as a recyclerview also
 */
// We also need to modify the adapter so that it can have a reference to the homefeed
class MainAdapter(val homeFeed: HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {

    private val videoTitles = listOf("First title", "Second", "3rd", "4th", "5th")

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    // Number of items
    override fun getItemCount(): Int {
        return homeFeed.videos.count()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val video = homeFeed.videos.get(position)
        holder.view.textView_video_title?.text = video.name

        holder.view.textView_channel_name.text = video.channel.name + " • " + "20K Views \n4days ago"

        // Get the imageview and then load things onto it
        val thumbnailImageView = holder.view.imageView_video_thumbnail
        // Use Picasso to load the image and put it in the ImageView
        Picasso.get().load(video.imageUrl).into(thumbnailImageView)

        // Do the same with the profile channel imageview
        // Change the image for the channel profile
        val channelProfileImageView = holder.view.imageView_channel_profile
        Picasso.get().load(video.channel.profileImageUrl).into(channelProfileImageView)

        holder.video = video
    }
}

// Provide the clicking behaviour
class CustomViewHolder(val view: View, var video: Video? = null) : RecyclerView.ViewHolder(view) {

    companion object {
        val VIDEO_TITLE_KEY = "VIDEO_TITLE"
        val VIDEO_ID_KEY = "VIDEO_ID"
    }

    // Here you can access the properties that belong to this class
    init {
        view.setOnClickListener {
            // Create the intent
            val intent = Intent(view.context, CourseDetailActivity::class.java)

            intent.putExtra(VIDEO_TITLE_KEY, video?.name)
            intent.putExtra(VIDEO_ID_KEY, video?.id)

            view.context.startActivity(intent)
        }
    }
}