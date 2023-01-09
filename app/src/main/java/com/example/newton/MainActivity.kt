package com.example.newton

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.viewpager2.widget.ViewPager2
import androidx.work.*
import com.example.newton.R
import com.example.newton.adapter.TabAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var profileBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mToolbar = findViewById<Toolbar>(R.id.main_toolbar)
        setSupportActionBar(mToolbar)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.pager)

        val tabTitles = resources.getStringArray(R.array.tabTitles)
        viewPager.adapter = TabAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = tabTitles[0]
                1 -> tab.text = tabTitles[1]
                2 -> tab.text = tabTitles[2]
            }
        }.attach()

        val sharedPrefs = getSharedPreferences("prod", Context.MODE_PRIVATE)
        val isSignedIn = sharedPrefs.getBoolean("is_signed_in", false)

        if(!isSignedIn){
            val newIntent = Intent(this, LoginActivity::class.java)
            newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(newIntent)
        }

        myWorkManager()
    }

    private fun myWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val myRequest = PeriodicWorkRequest.Builder(
            NotifWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("my_id", ExistingPeriodicWorkPolicy.KEEP,
            myRequest)
    }

    private fun simpleWork() {
        val mRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<NotifWorker>()
            .build()

        WorkManager.getInstance(this)
            .enqueue(mRequest)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val myView = findViewById<View>(R.id.main_toolbar)
        when(item.itemId){
            R.id.profile -> {
                val newIntent = Intent(this, ProfileActivity::class.java)
                startActivity(newIntent)

                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.toolbar), menu)
        return super.onCreateOptionsMenu(menu)
    }


}