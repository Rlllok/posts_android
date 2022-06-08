package com.example.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posts.views.CommentsListView
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {
    lateinit var viewModel: CommentsListView
    private var commentsListAdapter = CommentsListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val bundle : Bundle?= intent.extras
        val id = bundle!!.getInt("post_id")
        val title = bundle.getString("post_title")
        val owner = bundle.getString("post_owner")
        val content = bundle.getString("post_content")

        postTitle.text = title
        postOwner.text = "Author: ${owner}"
        postContent.text = content

        commentsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentsListAdapter
        }

        viewModel = ViewModelProvider(this).get(CommentsListView::class.java)
        viewModel.refresh(id)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.comments.observe(this, Observer { comments ->
            comments?.let {
                commentsList.visibility = View.VISIBLE
                commentsListAdapter.updateComments(it)
            }
        })
    }
}