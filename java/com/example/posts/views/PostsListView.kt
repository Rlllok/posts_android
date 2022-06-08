package com.example.posts.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posts.api.PostsApi
import com.example.posts.api.RetrofitHelper
import com.example.posts.models.Post
import kotlinx.coroutines.*

class PostsListView : ViewModel() {
    var postsApi = RetrofitHelper.getInstance().create(PostsApi::class.java)
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val posts = MutableLiveData<List<Post>>()
    val postsLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchPosts()
    }

    private fun fetchPosts() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = postsApi.getPosts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    posts.value = response.body()
                    postsLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
        postsLoadError.value = ""
        loading.value = false
    }

    private fun onError(s: String) {
        postsLoadError.value = s
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}