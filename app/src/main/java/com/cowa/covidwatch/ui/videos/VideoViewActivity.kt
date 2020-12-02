package com.cowa.covidwatch.ui.videos

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cowa.covidwatch.MainActivity
import com.cowa.covidwatch.R
import com.cowa.covidwatch.model.Comment
import com.cowa.covidwatch.model.PostCommentBody
import com.cowa.covidwatch.model.Video
import com.cowa.covidwatch.service.DataService
import com.cowa.covidwatch.ui.articles.CommentsRecyclerAdapter
import com.cowa.covidwatch.util.StringUtils
import kotlinx.android.synthetic.main.activity_video_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class VideoViewActivity : AppCompatActivity() {


    private lateinit var video: Video
    private lateinit var adapter: CommentsRecyclerAdapter
    private lateinit var commentList: ArrayList<Comment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           video_content_tv.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        }

        video = intent.getParcelableExtra<Video>("video")
        video_title_tv.text =  video.title
        andExoPlayerView.setSource(video.video)
        andExoPlayerView.setShowFullScreen(true)


        video_content_tv.text = video.description
        video_writer_name.text = video.user.first_name + video.user.last_name
        video_writer_mail.text = video.user.email
        video_timeago.text = StringUtils.stringTimeStampToTimeAgo(video.timestamp)

        post_video_comment_btn.setOnClickListener {
            addComment()
        }
        video_comment_edit.setOnFocusChangeListener { v, hasFocus ->
            scroll_view_video.scrollTo(0, video_comment_edit.y.toInt())
        }
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        var recyclerView = video_comments_listview
        commentList = arrayListOf()
        commentList.addAll(video.comments)
        adapter = CommentsRecyclerAdapter(this, commentList)
        recyclerView.adapter = adapter

        val manager = LinearLayoutManager(this)
        //reverse showing order
        manager.reverseLayout = true;
        manager.stackFromEnd = true;
        manager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            manager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }



    fun addComment() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://corona-watch-esi.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retrofit.create(DataService::class.java)


        val commentRequest = service.makecommentTmp(PostCommentBody(video_comment_edit.text.toString(),video.id))

        // executer la requete post
        commentRequest?.enqueue(object : Callback<Comment?> {
            override fun onFailure(call: Call<Comment?>, t: Throwable) {
                Toast.makeText(MainActivity.context,"error posting comment", Toast.LENGTH_SHORT).show()

            }
            override fun onResponse(call: Call<Comment?>, response: Response<Comment?>) {
                Toast.makeText(MainActivity.context,"comment posted", Toast.LENGTH_SHORT).show()
                video_comment_edit.text.clear()
                val imm = MainActivity.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
                if (response!=null) commentList.add(response.body()!!)
                adapter.notifyDataSetChanged()
                video_comments_listview.scrollToPosition(commentList.size-1)

            }
        })
    }
}
