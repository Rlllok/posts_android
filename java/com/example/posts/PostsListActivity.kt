package com.example.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posts.api.PostsApi
import com.example.posts.api.RetrofitHelper
import com.example.posts.models.Post
import com.example.posts.views.PostsListView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.activity_posts_list.*

class PostsListActivity : AppCompatActivity() {
    lateinit var viewModel: PostsListView
    private var postsListAdapter = PostsListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_list)
        viewModel = ViewModelProvider(this).get(PostsListView::class.java)
        viewModel.refresh()

        postsListAdapter.setOnItemClickListener(object : PostsListAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@PostsListActivity, PostActivity::class.java)
                intent.putExtra("post_id", viewModel.posts.value?.get(position)?.id)
                intent.putExtra("post_title", viewModel.posts.value?.get(position)?.title)
                intent.putExtra("post_owner", viewModel.posts.value?.get(position)?.owner)
                intent.putExtra("post_content", viewModel.posts.value?.get(position)?.content)
                startActivity(intent)
                Log.d("ayush: ", "${position}")
            }
        })

        postsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postsListAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.posts.observe(this, Observer { posts ->
            posts?.let {
                postsList.visibility = View.VISIBLE
                postsListAdapter.updatePosts(it)
            }
        })

        viewModel.postsLoadError.observe(this, Observer { isError ->
            listError.visibility = if(isError == "") View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    postsList.visibility = View.GONE
                }
            }
        })
    }
}