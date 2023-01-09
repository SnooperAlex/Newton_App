package com.example.newton

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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

class SearchFragment : Fragment() {
    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imageList = mutableListOf<String>()
    private var linksList = mutableListOf<String>()

    lateinit var searchview : SearchView

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
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchview = view.findViewById(R.id.searchBar)

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                GlobalScope.launch(Dispatchers.IO) {
                    val response = query?.let { api.searchNews(it, "qJeaftCDwwSlcrGxO32Oj05AsVG7_MGH1vKipJ7jxUKwBdoT") }
                    if (response != null) {
                        for (article in response.news) {
                            Log.i("ForYouFragment", "Result = $article")
                            addToList(article.title, article.description, article.image, article.url)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        setRecView()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                clearList()
                setRecView()
                return true
            }

        })

        return view
    }

    private fun setRecView(){
        val recyclerItems = view?.findViewById<RecyclerView>(R.id.recViewSearch)
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