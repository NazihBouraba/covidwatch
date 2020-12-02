package com.cowa.covidwatch.ui.articles

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cowa.covidwatch.MainActivity
import com.cowa.covidwatch.MainActivity.Companion.context
import com.cowa.covidwatch.R
import com.cowa.covidwatch.service.DataService
import com.cowa.covidwatch.model.Article
import com.cowa.covidwatch.model.Comment
import com.cowa.covidwatch.model.PostCommentBody
import com.cowa.covidwatch.service.RetrofitClientInstance
import com.cowa.covidwatch.util.StringUtils
import com.squareup.moshi.Json
import kotlinx.android.synthetic.main.activity_article_view.*
import kotlinx.android.synthetic.main.comment_list_item.*
import kotlinx.android.synthetic.main.fragment_articles.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class ArticleViewActivity : AppCompatActivity() {


    private lateinit var article: Article
    private lateinit var adapter: CommentsRecyclerAdapter
    private lateinit var commentList: ArrayList<Comment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_view)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            article_content_tv.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        article = intent.getParcelableExtra<Article>("article")
        article_title_tv.text =  article.title
        article_content_tv.text = article.content
        article_writer_name.text = article.writer.first_name + article.writer.last_name
        article_writer_mail.text = article.writer.email
        article_timeago.text = StringUtils.stringTimeStampToTimeAgo(article.timestamp)

        post_article_comment_btn.setOnClickListener {
            addComment()
        }
        article_comment_edit.setOnFocusChangeListener { v, hasFocus ->
            scroll_view_article.scrollTo(0, article_comment_edit.y.toInt())
        }

        //parsing List of Image to List of String(url)
        var images_list = ArrayList<String>()
        for (i in article.images)
        {
            images_list.add(i.content)
        }
        var slider_image_list = images_list.toList()
        slider.setItems(slider_image_list)
        slider.getIndicator()
        // automatique animation
        slider.addTimerToSlide(5000)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        var recyclerView = article_comments_listview
        commentList = arrayListOf()
        commentList.addAll(article.comments)
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

        //issue is here

//        val commentRequest = service.makecommentTemp(article_comment_edit.text.toString(),true,article.id)
        val commentRequest = service.makecommentTmp(PostCommentBody(article_comment_edit.text.toString(),article.id))

        // executer la requete post
        commentRequest?.enqueue(object : Callback<Comment?> {
            override fun onFailure(call: Call<Comment?>, t: Throwable) {
                Toast.makeText(context,"error posting comment",Toast.LENGTH_SHORT).show()

            }
            override fun onResponse(call: Call<Comment?>, response: Response<Comment?>) {
                Toast.makeText(context,"comment posted",Toast.LENGTH_SHORT).show()
                article_comment_edit.text.clear()
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
                if (response!=null) commentList.add(response.body()!!)
                adapter.notifyDataSetChanged()
                article_comments_listview.scrollToPosition(commentList.size-1)

            }
        })
    }

}
