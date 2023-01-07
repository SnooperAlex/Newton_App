package com.example.newton

import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newton.adapters.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org/"

class ForYouFragment : Fragment() {

    lateinit var countDownTimer: CountDownTimer

    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imageList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_foryou, container, false)
        val api  =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RequestAPI::class.java)
        GlobalScope.launch(Dispatchers.IO){
            try {
                val response = api.getNews()

                for(article in response.articles){
                    Log.i("ForYouFragment", "Result = $article")
                    addToList(article.author, article.title, article.publishedAt, article.url)
                }
                withContext(Dispatchers.Main){
                    val recyclerItems = view.findViewById<RecyclerView>(R.id.recView)
                    recyclerItems.hasFixedSize()
                    recyclerItems.layoutManager = LinearLayoutManager(context)
                    recyclerItems.adapter = RecyclerAdapter(titleList, descList, imageList, linksList)
                }
            }
            catch (e:Exception){
                Log.e("ForYouFragment", e.toString())
            }
        }


        return view

    }

    private fun addToList(title: String, desc: String, image: String, link: String){
        titleList.add(title)
        descList.add(desc)
        imageList.add(image)
        linksList.add(link)

    }



}

