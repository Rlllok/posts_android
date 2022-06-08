package com.example.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.posts.models.Comment
import kotlinx.android.synthetic.main.item_comment.*
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentsListAdapter (var comments: ArrayList<Comment>): RecyclerView.Adapter<CommentsListAdapter.CommentViewHolder>() {

    fun updateComments(newComments: List<Comment>) {
        comments.clear()
        comments.addAll(newComments)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)

        return CommentViewHolder(view)
    }
    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }
    class CommentViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val owner = view.commentOwner
        private val content = view.commentContent

        fun bind(comment: Comment) {
            owner.text = comment.owner
            content.text = comment.text
        }
    }
}