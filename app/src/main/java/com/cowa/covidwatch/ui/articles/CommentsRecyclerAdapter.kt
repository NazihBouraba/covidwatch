package com.cowa.covidwatch.ui.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cowa.covidwatch.R
import com.cowa.covidwatch.model.Article
import com.cowa.covidwatch.model.Comment
import com.cowa.covidwatch.util.Communicator
import com.cowa.covidwatch.util.StringUtils
import kotlinx.android.synthetic.main.article_list_item.view.*
import kotlinx.android.synthetic.main.comment_list_item.view.*

class CommentsRecyclerAdapter(private val context: Context?, private var list: List<Comment>) :
    RecyclerView.Adapter<CommentsRecyclerAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.comment_list_item, parent, false)
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
        lateinit var current: Comment

        fun setData(current: Comment, position: Int) {
            itemView.comment_content.text = current.content
            itemView.commenter_name.text = current.user
            this.pos = position
            this.current = current
        }

        fun setListeners() {
            itemView.setOnClickListener {

            }
        }
    }
}
