package com.example.posts.views

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.posts.api.PostsApi
import com.example.posts.api.RetrofitHelper
import com.example.posts.models.Comment
import kotlinx.coroutines.*

class CommentsListView : ViewModel() {

    var postsApi = RetrofitHelper.getInstance().create(PostsApi::class.java)
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val comments = MutableLiveData<List<Comment>>()
    val commentsLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(id: Int) {
        fetchComments(id)
    }

    private fun fetchComments(id: Int) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = postsApi.getCommentsToPost(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("ayush: ", "${response.body()}")
                    comments.value = response.body()
                    commentsLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
        commentsLoadError.value = ""
        loading.value = false
    }

    private fun onError(s: String) {
        commentsLoadError.value = s
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}