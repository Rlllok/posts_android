package com.example.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posts.models.Post
import kotlinx.android.synthetic.main.item_post.*
import kotlinx.android.synthetic.main.item_post.view.*

class PostsListAdapter (var posts: ArrayList<Post>): RecyclerView.Adapter<PostsListAdapter.PostViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    fun updatePosts(newPosts: List<Post>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)

        return PostViewHolder(view, mListener)
    }
    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }
    class PostViewHolder(view: View, listener: onItemClickListener): RecyclerView.ViewHolder(view) {
        private val postTitle = view.postTitle
        private val postOwner = view.postOwner
        private val postContent = view.postContent

        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(post: Post) {
            postTitle.text = post.title
            postOwner.text = "Author: ${post.owner}"
            if (post.content.length <= 150)
                postContent.text = post.content
            else
                postContent.text = "${post.content.take(150)} ..."
        }
    }
}