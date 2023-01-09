package com.example.newton

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.newton.ForYouFragment.Companion.categories
import com.example.newton.ForYouFragment.Companion.idList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotifWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val testIDList = mutableListOf<String>()

        var api  =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RequestAPI::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            if (categories.isEmpty()) {
                val response = api.getLatestNews()
                for (article in response.news) {
                    Log.i("ForYouFragment", "Result = $article")
                    testIDList.add(article.id)
                }
            }
            else{
                for (i in categories) {
                    val response = api.getFilteredNews(i)
                    for (article in response.news) {
                        Log.i("ForYouFragment", "Result = $article")
                        testIDList.add(article.id)
                    }
                }
            }
        }

        if (testIDList != idList){
            if (categories.isEmpty()) {
                makeStatusNotification(
                    "More news awaits you, read all the latest news ",
                    applicationContext
                )
            }else{
                makeStatusNotification(
                    "More news awaits you, read about " + categories.random(),
                    applicationContext)
            }
        }
        return Result.success()
    }
}