package com.example.newton

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity: AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    lateinit var emailText: EditText
    lateinit var passwordText: EditText
    lateinit var loginBtn: Button
    lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        emailText = findViewById(R.id.username)
        passwordText = findViewById(R.id.password)
        loginBtn = findViewById(R.id.submitBtn)
        registerBtn = findViewById(R.id.regBtn)

        registerBtn.setOnClickListener { v -> registerClick(v) }

    }

    override fun onStart() {
        super.onStart()
        update()
    }

    private fun update() {
        currentUser = mAuth.currentUser

        val currentEmail = currentUser?.email
    }

    fun registerClick(view: View) {
        if(mAuth.currentUser != null){
            displayMessage(view, "Blablabla")
        }
        else {
            mAuth.createUserWithEmailAndPassword(
                emailText.text.toString(), passwordText.text.toString()
            ).addOnCompleteListener(this) {task ->
                if (task.isSuccessful){
                    update()
                }
                else{
                    displayMessage(loginBtn, "egergrgtrgtrg")
                }
            }
        }
    }

    private fun displayMessage(view: View, s: String) {
        val sb = Snackbar.make(view,s, Snackbar.LENGTH_SHORT)
        sb.show()
    }

}