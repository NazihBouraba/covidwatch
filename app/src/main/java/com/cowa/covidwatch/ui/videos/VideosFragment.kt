package com.cowa.covidwatch.ui.videos

import android.os.Bundle
import android.provider.MediaStore
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
import com.cowa.covidwatch.model.DataProviderVideo
import com.cowa.covidwatch.model.Video
import com.cowa.covidwatch.service.DataService
import com.cowa.covidwatch.service.RetrofitClientInstance
import kotlinx.android.synthetic.main.fragment_articles.view.*
import kotlinx.android.synthetic.main.fragment_videos.*
import kotlinx.android.synthetic.main.fragment_videos.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class VideosFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var videosViewModel: VideosViewModel
     var videolist = DataProviderVideo.data

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        videosViewModel =
                ViewModelProviders.of(this).get(VideosViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_videos, container, false)
        videosViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        setupRecyclerView(root)
        root.swipe_container_videos.setOnRefreshListener(this)
        return root
    }



    private fun setupRecyclerView(rootView: View) {

       getvideosList()
        val recyclerView = rootView.findViewById(R.id.videos_recyclerview) as RecyclerView
//
      val adapter = VideosRecyclerAdapter(context, videolist)
       recyclerView.adapter = adapter
       val manager = LinearLayoutManager(activity)
       manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
    }

    override fun onRefresh() {
        getvideosList()
        swipe_container_videos.isRefreshing = false
    }


    override fun onResume() {
        super.onResume()
        onRefresh()
    }




    fun getvideosList() {

        //make progress spinner visible
        if (video_progressBar != null)
            video_progressBar.visibility = View.VISIBLE
        //creer une instance du client retrofit
        val retrofit = RetrofitClientInstance.retrofitInstance
        //creer une instance du service
        val service = retrofit.create(DataService::class.java)

        // creer la requete get
        val videoRequest = service.listvideo()


        // executer la requete get
        videoRequest.enqueue(object : Callback<List<Video>> {
            override fun onResponse(call: Call<List<Video>>, response: Response<List<Video>>) {
                if (video_progressBar != null)
                    video_progressBar.visibility = View.GONE
                val allvideos = response.body()
                if (allvideos != null) {
                    videolist = allvideos
                    var newadapter = VideosRecyclerAdapter(context, videolist)
                    videos_recyclerview.swapAdapter(newadapter, true)


                }
            }

            override fun onFailure(call: Call<List<Video>>, t: Throwable) {
                if (video_progressBar != null)
                    video_progressBar.visibility = View.GONE
                Toast.makeText(MainActivity.context, "error getting videos", Toast.LENGTH_SHORT)
                    .show()

            }
        })

    }
}
