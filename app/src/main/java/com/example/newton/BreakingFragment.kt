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

class BreakingFragment : Fragment() {
    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imageList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()


    var api  = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RequestAPI::class.java)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_breaking, container, false)
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.searchNews("Latest", "qJeaftCDwwSlcrGxO32Oj05AsVG7_MGH1vKipJ7jxUKwBdoT")
            for (article in response.news) {
                Log.i("ForYouFragment", "Result = $article")
                addToList(article.title, article.description, article.image, article.url)
            }
            withContext(Dispatchers.Main) {
                setRecView()
            }
        }
        val swipeToRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshBreaking)
        swipeToRefresh.setOnRefreshListener {
            updateNews()
            swipeToRefresh.isRefreshing = false
        }
        return view
    }

    private fun setRecView(){
        val recyclerItems = view?.findViewById<RecyclerView>(R.id.recViewBreaking)
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
            val response = api.searchNews("Latest", "qJeaftCDwwSlcrGxO32Oj05AsVG7_MGH1vKipJ7jxUKwBdoT")
            clearList()
            for (article in response.news) {
                Log.i("ForYouFragment", "Result = $article")
                addToList(
                    article.title,
                    article.description,
                    article.image,
                    article.url
                )
            }
        }
        val recyclerItems = view?.findViewById<RecyclerView>(R.id.recViewBreaking)
        if (recyclerItems != null) {
            recyclerItems.adapter?.notifyDataSetChanged()
        }


    }

    private fun addToList(title: String, desc: String, image: String, link: String){
        titleList.add(title)
        descList.add(desc)
        imageList.add(image)
        linksList.add(link)


    }

    private fun clearList(){
        titleList.clear()
        descList.clear()
        imageList.clear()
        linksList.clear()

    }
}