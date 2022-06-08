package com.example.posts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.posts.api.PostsApi
import com.example.posts.api.RetrofitHelper
import com.example.posts.models.LogInBody
import com.example.posts.models.RegistrationBody
import com.example.posts.models.RegistrationResponse
import com.example.posts.models.Token
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    var postsApi = RetrofitHelper.getInstance().create(PostsApi::class.java)
    val regResponse = MutableLiveData<RegistrationResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        activity_main_regButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val fullname = fullnameEditText.text.toString().trim()
            val bio = bioEditText.text.toString().trim()
            val date = dateEditText.text.toString().trim()
            val regInfo = RegistrationBody(email, username, password, fullname, date, bio)
            postsApi.createAccount(regInfo).enqueue(object: Callback<RegistrationResponse> {
                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                    regResponse.value = response.body()
                    Log.d("ayush: ", regResponse.value.toString())
                }
            })
            if (regResponse.value != null) {
                Toast.makeText(applicationContext,"Account created.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext,"Wrong Email or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}