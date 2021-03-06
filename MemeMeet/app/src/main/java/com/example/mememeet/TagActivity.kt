package com.example.mememeet

import android.annotation.SuppressLint
import android.app.Activity
import android.app.WallpaperManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import java.io.IOException
import kotlin.properties.Delegates

const val REQUEST_CODE=1000

class TagActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeButton: ImageButton
    private lateinit var addImageButton: ImageButton
    private lateinit var tagText:TextView
    private lateinit var adapter: PostAdapter

    private val posts: MutableList<Post> = mutableListOf()
    private val client = OkHttpClient()
    private val moshi= Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val postJsonAdapter: JsonAdapter<Post> = moshi.adapter(Post::class.java)
    private val tagPostJsonAdapter: JsonAdapter<TagPost> = moshi.adapter(TagPost::class.java)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)

        recyclerView=findViewById(R.id.tagRecyclerView)
        homeButton=findViewById(R.id.tagToHomeButton)
        addImageButton=findViewById(R.id.addImageButton)
        tagText=findViewById(R.id.tagName)

        val id=intent.extras?.getInt("user")
        val userName=intent.extras?.getString("userName")
        val tag=intent.extras?.getInt("tag")
        val tagName=intent.extras?.getString("tagName")

        tagText.text= "#$tagName"

        homeButton.setOnClickListener {
            val homeIntent= Intent(this,MainActivity::class.java)
            homeIntent.putExtra("user",id)
            startActivity(homeIntent)
        }

        addImageButton.setOnClickListener {
            val memeIntent=Intent(this,MemeActivity::class.java)
            memeIntent.putExtra("tag",tag)
            memeIntent.putExtra("tagName",tagName)
            memeIntent.putExtra("user",id)
            memeIntent.putExtra("userName",userName)
            startActivity(memeIntent)
        }

        populateTagList()
        recyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun populateTagList() {
        val tag=intent.extras?.getInt("tag")
        val requestGet = Request.Builder().url(BASE_URL+"post/tag/"+tag.toString()+"/").build()
        client.newCall(requestGet).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        throw IOException("Network call unsuccessful")
                    }
                    val postList = tagPostJsonAdapter.fromJson(response.body!!.string())!!
                    for (post in postList.posts) {
                        posts.add(Post(post.id,post.caption,post.image,post.user,Tag(postList.id,postList.tag)))
                    }
                    adapter = PostAdapter(posts)
                    runOnUiThread {
                        recyclerView.adapter = adapter
                    }
                }
            }
        })
    }
}