package com.cowa.covidwatch.ui.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cowa.covidwatch.R
import com.cowa.covidwatch.model.Article
import com.cowa.covidwatch.util.Communicator
import com.cowa.covidwatch.util.StringUtils
import kotlinx.android.synthetic.main.article_list_item.view.*

class ArticlesRecyclerAdapter(private val context: Context?, private var list: List<Article>) :
    RecyclerView.Adapter<ArticlesRecyclerAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.article_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = list[position]
        holder.setData(current, position)
        holder.setListeners()
    }

    override fun getItemCount(): Int = list.size

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var pos: Int = 0
        lateinit var current: Article

        fun setData(current: Article, position: Int) {
            itemView.article_li_title.text = current.title
            if (context != null && current.images[0]!=null) {
                Glide.with(context)
                    .load(current.images[0].content)
                    .into(itemView.article_li_thumbnail);
                //current.images[0].content
            }
            itemView.article_li_upload_date.text = StringUtils.stringTimeStampToTimeAgo(current.timestamp)
        //    itemView.article_li_thumbnail.setImageResource(current.images[0])
            this.pos = position
            this.current = current
        }

        fun setListeners() {

            itemView.setOnClickListener {
                val myCommunicator = context as Communicator
                myCommunicator.displayArticleDetails(current)
            }
        }
    }
}
