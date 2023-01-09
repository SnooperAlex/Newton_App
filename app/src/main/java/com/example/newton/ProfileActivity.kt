package com.example.newton

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity(){
    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    lateinit var logOutBtn: Button
    lateinit var topicsBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logOutBtn = findViewById(R.id.logOutBtn)
        topicsBtn = findViewById(R.id.topics)

        logOutBtn.setOnClickListener{v -> logOut(v)}
        topicsBtn.setOnClickListener{v -> topics(v)}
    }

    private fun topics(v: View?) {
        val newIntent = Intent(this, TopicsActivity::class.java)
        startActivity(newIntent)
    }

    private fun logOut(v: View?) {

        val sharedPrefs = getSharedPreferences("prod", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()){
            putBoolean("is_signed_in", false)
            commit()
        }
        mAuth.signOut()
        val newIntent = Intent(this, LoginActivity::class.java)
        newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(newIntent)
    }
}