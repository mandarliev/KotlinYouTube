package com.example.android.kotlinyoutube

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // First thing we need is a Linear layoutManager
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        /*
        Set an adapter on the recyclerView
        What is an adapter?
        It is kind of a data source or a UI tableview delegate for ur list
        It helps you render your items inside ur list
        This adapter is created in a seperate class
         */
//        recyclerView_main.adapter = MainAdapter()

        fetchJson()
    }

    /*
    Let's fetch the JSON
    Used the following link: http://square.github.io/okhttp/
    There you can in the examples section how to do it
    I just turned it into Kotlin
     */
    fun fetchJson() {
        println("Attempting to fetch JSON")

        // This is the URL with the JSON data
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        // Turn it into a request so that we can use it in the client below
        val request = Request.Builder().url(url).build()

        // Create a client
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()
                println(body)

                // Construct the HomeFeed object from the body string using Gson builder
                // Check here https://guides.codepath.com/android/leveraging-the-gson-library
                // That's dope though
                val gson = GsonBuilder().create()

                // Below is the coolest thing ever dude
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                // Just make it run on the UI thread(ANR stuff u know)
                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(homeFeed)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }
}

