package com.example.newton

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newton.adapters.RecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.currentsapi.services/"

class ForYouFragment : Fragment() {

    private val CHANNEL_ID = "com.example.newton.ONE"
    private val notifID = 101


    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imageList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()

    companion object {
        var categories = mutableListOf<String>()
        val idList = mutableListOf<String>()
    }
    var api  =Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RequestAPI::class.java)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_foryou, container, false)
        GlobalScope.launch(Dispatchers.IO) {
            if (categories.isEmpty()) {
                val response = api.getLatestNews()
                for (article in response.news) {
                    Log.i("ForYouFragment", "Result = $article")
                    addToList(article.title, article.description, article.image, article.url, article.id)
                }
            }
            else{
                for (i in categories) {
                    val response = api.getFilteredNews(i)
                    for (article in response.news) {
                        Log.i("ForYouFragment", "Result = $article")
                        addToList(article.title, article.description, article.image, article.url, article.id)
                    }
                }
            }
            withContext(Dispatchers.Main) {
                setRecView()
            }
        }
        val swipeToRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)
        swipeToRefresh.setOnRefreshListener {
            updateNews()
            swipeToRefresh.isRefreshing = false
        }
        return view
    }

    private fun setRecView(){
        val recyclerItems = view?.findViewById<RecyclerView>(R.id.recView)
        if (recyclerItems != null) {
            recyclerItems.hasFixedSize()
        }
        if (recyclerItems != null) {
            recyclerItems.layoutManager = LinearLayoutManager(context)
        }
        if (recyclerItems != null) {
            recyclerItems.adapter = RecyclerAdapter(titleList, descList, imageList, linksList)
        }
    }

    private fun updateNews(){

        GlobalScope.launch(Dispatchers.IO) {
            if (categories.isEmpty()) {
                val response = api.getLatestNews()
                clearList()
                for (article in response.news) {
                    Log.i("ForYouFragment", "Result = $article")
                    addToList(article.title, article.description, article.image, article.url, article.id)
                }
            }
            else{
                for (i in categories) {
                    val response = api.getFilteredNews(i)
                    clearList()
                    for (article in response.news) {
                        Log.i("ForYouFragment", "Result = $article")
                        addToList(article.title, article.description, article.image, article.url, article.id)
                    }
                }
            }
            }
            val recyclerItems = view?.findViewById<RecyclerView>(R.id.recView)
            if (recyclerItems != null) {
                recyclerItems.adapter?.notifyDataSetChanged()
            }


    }

    private fun addToList(title: String, desc: String, image: String, link: String, id: String){
        titleList.add(title)
        descList.add(desc)
        imageList.add(image)
        linksList.add(link)
        idList.add(id)

    }

    private fun clearList(){
        titleList.clear()
        descList.clear()
        imageList.clear()
        linksList.clear()
        idList.clear()
    }

}

