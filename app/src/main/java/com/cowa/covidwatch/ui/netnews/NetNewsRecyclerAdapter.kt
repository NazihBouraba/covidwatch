package com.cowa.covidwatch.ui.netnews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.cowa.covidwatch.R
import com.cowa.covidwatch.model.DMThumbnail
import com.cowa.covidwatch.model.YtVideo
import com.cowa.covidwatch.service.DataService
import com.cowa.covidwatch.util.Communicator
import com.cowa.covidwatch.util.StringUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ytvideo_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


final class NetNewsRecyclerAdapter(private val context: Context?, private var list: List<YtVideo>) :
    RecyclerView.Adapter<NetNewsRecyclerAdapter.MyViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NetNewsRecyclerAdapter.MyViewHolder {
        val view = inflater.inflate(R.layout.ytvideo_list_item, parent, false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = list[position]
        holder.setData(current, position)
        holder.setListeners(current)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var pos: Int = 0
        lateinit var current: YtVideo
        var s = "String"

        @SuppressLint("SetTextI18n")
        fun setData(current: YtVideo, position: Int) {
            itemView.ytvideo_li_title.text = current.title
            itemView.ytvideo_li_upload_date.text = StringUtils.stringTimeStampToTimeAgo(current.timestamp)

           if(isfromyoutube(current)) {
               var i = "https://img.youtube.com/vi/" + current.youtube_id + "/mqdefault.jpg"

               if (context != null) {
                   Glide.with(context)
                       .load(i)
                       .into(itemView.ytvideo_li_thumbnail);
               }
           }

            else { get_thumbnail(current.youtube_id,current)



           }

            this.pos = position
            this.current = current
        }

        fun setListeners(current : YtVideo) {
            if (isfromyoutube(current)) {
                itemView.setOnClickListener {
                    val myCommunicator = context as Communicator
                    myCommunicator.displayYtVideoDetails(current)
                }
            } else {

                itemView.setOnClickListener {
                    val myCommunicator = context as Communicator
                    myCommunicator.displayDMVideoDetails(current)
                }
            }
        }

        fun isfromyoutube(yt: YtVideo): Boolean {
            if (yt.embed_link.contains("youtube")) {
                return true
            } else return false
        }


        private fun get_thumbnail(id : String,current : YtVideo ) : String {


            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.dailymotion.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            //creer une instance du service
            val service = retrofit.create(DataService::class.java)

            // creer la requete get
            val th = service.thumbnail(id)
            // executer la requete get
            th.enqueue(object : Callback<DMThumbnail> {
                override fun onResponse(call: Call<DMThumbnail>, response: Response<DMThumbnail>) {

                    val images = response.body()
                    if (images != null) {

                       Log.v("image",images.thumbnail_medium_url+"  merda")
                        if (context != null) {
                            Glide.with(context)
                                .load(images.thumbnail_medium_url)
                                .into(itemView.ytvideo_li_thumbnail);
                        }

                  //   Toast.makeText(context,"thumnail url is "+images.thumbnail_medium_url,Toast.LENGTH_SHORT).show()


                    }
                }

                override fun onFailure(call: Call<DMThumbnail>, t: Throwable) {
                   // Toast.makeText(context,"thumnail error"+s,Toast.LENGTH_SHORT).show()

                }
            })
            return s

        }


    }
}