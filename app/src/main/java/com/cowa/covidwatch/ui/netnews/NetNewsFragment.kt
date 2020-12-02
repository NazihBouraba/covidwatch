package com.cowa.covidwatch.ui.netnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cowa.covidwatch.MainActivity
import com.cowa.covidwatch.R
import com.cowa.covidwatch.model.DMThumbnail
import com.cowa.covidwatch.model.DMvideo

import com.cowa.covidwatch.model.YtVideo
import com.cowa.covidwatch.service.DataService
import com.cowa.covidwatch.service.RetrofitClientInstance
import com.cowa.covidwatch.ui.videos.VideosRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_net_news.*
import kotlinx.android.synthetic.main.fragment_net_news.view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NetNewsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var netNewsViewModel: NetNewsViewModel
    private val url = "https://corona-watch-esi.herokuapp.com/"
    var ytvideolist = YtVideo.DataProviderytVideo.data
    var dmvideolist = DMvideo.DMProviderVideo.data

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        netNewsViewModel =
                ViewModelProviders.of(this).get(NetNewsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_net_news, container, false)
        netNewsViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        setupRecyclerView(root)
        get_youtube_video_list()
        get_DM_video_list()


        root.swipe_container_net_news.setOnRefreshListener(this)
        return root
    }




    private fun setupRecyclerView(rootView: View) {


        val recyclerView = rootView.findViewById(R.id.net_news_recyclerview) as RecyclerView
        val adapter =NetNewsRecyclerAdapter(context, ytvideolist)
        recyclerView.adapter = adapter
        val manager = LinearLayoutManager(activity)
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager

    }

    override fun onRefresh() {

        get_youtube_video_list()
        get_DM_video_list()

        swipe_container_net_news.isRefreshing = false
    }


    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    fun embedlink_to_link(s:String):String {
        val i =0
        while (i<s.length){
            var c= s.get(i)

        }

    return "sdfsd"
    }

    private fun get_youtube_video_list() {
        //make progress spinner visible
        if (net_news_progressBar != null)
            net_news_progressBar.visibility = View.VISIBLE
        //creer une instance du client retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        //creer une instance du service
        val service = retrofit.create(DataService::class.java)

        // creer la requete get
        val ytvideoRequest = service.youtube_video_list()


        // executer la requete get
        ytvideoRequest.enqueue(object : Callback<List<YtVideo>> {
            override fun onResponse(call: Call<List<YtVideo>>, response: Response<List<YtVideo>>) {
                if (net_news_progressBar != null)
                    net_news_progressBar.visibility = View.GONE
                val allvideos = response.body()
                if (allvideos != null) {
                    ytvideolist = allvideos
                //    var newadapter = NetNewsRecyclerAdapter(context, ytvideolist)
               //     net_news_recyclerview.swapAdapter(newadapter, true)


                }
            }

            override fun onFailure(call: Call<List<YtVideo>>, t: Throwable) {
                if (net_news_progressBar != null)
                    net_news_progressBar.visibility = View.GONE
                Toast.makeText(MainActivity.context, "error getting youtube videos", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }


    private fun get_DM_video_list() {
        //make progress spinner visible
        if (net_news_progressBar != null)
            net_news_progressBar.visibility = View.VISIBLE
        //creer une instance du client retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        //creer une instance du service
        val service = retrofit.create(DataService::class.java)

        // creer la requete get
        val DMvideoRequest = service.daily_motion_video_list()


        // executer la requete get
        DMvideoRequest.enqueue(object : Callback<List<DMvideo>> {
            override fun onResponse(call: Call<List<DMvideo>>, response: Response<List<DMvideo>>) {
                if (net_news_progressBar != null)
                    net_news_progressBar.visibility = View.GONE
                val allvideos = response.body()
                if (allvideos != null) {
                    dmvideolist = allvideos
                    val netvideo : MutableList<YtVideo> = ArrayList()
                    netvideo.clear()
                    netvideo.addAll(ytvideolist)
                    netvideo.addAll(dmvideo_to_ytvideo(dmvideolist))
                    var newadapter = NetNewsRecyclerAdapter(context, netvideo)
                    net_news_recyclerview.swapAdapter(newadapter, true)


                }
            }

            override fun onFailure(call: Call<List<DMvideo>>, t: Throwable) {
                if (net_news_progressBar != null)
                    net_news_progressBar.visibility = View.GONE
                Toast.makeText(MainActivity.context, "error getting daily motion videos", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }



    fun dmvideo_to_ytvideo(dm : List<DMvideo>):List<YtVideo>{

       var ml = ArrayList<YtVideo>()
        for (i in dm) {
       //   var t =get_thumbnail(i.dailymotion_id)
          ml.add(YtVideo(i.dailymotion_id,i.id,i.title,i.timestamp,i.embed_link))

        }

        return  ml.toList()
    }



}
