package com.cowa.covidwatch.ui.videos

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.cowa.covidwatch.R
import com.cowa.covidwatch.model.Video
import com.cowa.covidwatch.util.Communicator
import com.cowa.covidwatch.util.StringUtils
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.android.synthetic.main.video_list_item.view.*


class VideosRecyclerAdapter(private val context: Context?, private var list: List<Video>) :
    RecyclerView.Adapter<VideosRecyclerAdapter.MyViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosRecyclerAdapter.MyViewHolder {
        val view = inflater.inflate(R.layout.video_list_item, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = list[position]
        holder.setData(current, position)
        holder.setListeners()
    }

//    @Throws(Throwable::class)
//    fun retriveVideoFrameFromVideo(videoPath: String?) {
//        Glide.with(context)
//            .asBitmap()
//            .load(videoPath)
//            .diskCacheStrategy(DiskCacheStrategy.DATA)
//            .into(iv_picture);
//    }





    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var pos: Int = 0
        lateinit var current: Video

        @SuppressLint("SetTextI18n")
        fun setData(current: Video, position: Int) {
            itemView.video_li_title.text = current.title + current.description
            itemView.video_li_upload_date.text = StringUtils.stringTimeStampToTimeAgo(current.timestamp)


            if(context!= null) {
                val thumbnailRequest: RequestBuilder<Drawable> = Glide
                    .with(context)
                    .load(current.video)

                Glide
                    .with(context)
                    .load(current.video)
                    .thumbnail(thumbnailRequest)
                    .into(itemView.video_li_thumbnail)
            }

            this.pos = position
            this.current = current
        }

        fun setListeners() {

            itemView.setOnClickListener {
                val myCommunicator = context as Communicator
                myCommunicator.displayVideoDetails(current)
            }
        }
    }
}