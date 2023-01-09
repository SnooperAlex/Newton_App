package com.example.newton

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.newton.ForYouFragment.Companion.categories
import com.google.android.gms.common.util.ArrayUtils.removeAll


class TopicsActivity: AppCompatActivity() {
    
    private var colourRed = "#a3333d"

    private var foodBtnstate = false
    private var fashionBtnstate = false
    private var enterBtnstate = false
    private var cultureBtnstate = false
    private var sportsBtnstate = false
    private var programBtnstate = false

    lateinit var foodButton: Button
    lateinit var fashionButton: Button
    lateinit var  enterButton: Button
    lateinit var  cultureButton: Button
    lateinit var sportsButton: Button
    lateinit var programButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        categories.removeAll(categories)

        foodButton = findViewById(R.id.foodFilter)
        fashionButton = findViewById(R.id.fashionFilter)
        enterButton = findViewById(R.id.enterFilter)
        cultureButton = findViewById(R.id.cultFilter)
        sportsButton = findViewById(R.id.sportFilter)
        programButton = findViewById(R.id.progFilter)

        foodButton.setOnClickListener {
            updateFoodBtnUI(foodButton, "food")
        }

        fashionButton.setOnClickListener {
            updateFashBtnUI(fashionButton, "fashion")
        }

        enterButton.setOnClickListener {
            updateEnterBtnUI(enterButton, "entertainment")
        }

        cultureButton.setOnClickListener {
            updateCultBtnUI(cultureButton, "culture")
        }

        sportsButton.setOnClickListener {
            updateSportsBtnUI(sportsButton, "sports")
        }

        programButton.setOnClickListener {
            updateProgramBtnUI(programButton, "programming")
        }
    }

    enum class State {
        ON,
        OFF
    }

    private fun updateFoodBtnUI(button: Button, string: String) {
        if (foodBtnstate) {
                button.setBackgroundColor(getColor(R.color.gray))
                categories.remove(string)
                foodBtnstate = false
            }
        else{
                button.setBackgroundColor(getColor(R.color.red))
                categories.add(string)
                foodBtnstate = true
            }

    }
    private fun updateFashBtnUI(button: Button, string: String) {
        if (fashionBtnstate) {
            button.setBackgroundColor(getColor(R.color.gray))
            categories.remove(string)
            fashionBtnstate = false
        }
        else{
            button.setBackgroundColor(getColor(R.color.red))
            categories.add(string)
            fashionBtnstate = true
        }

    }
    private fun updateEnterBtnUI(button: Button, string: String) {
        if (enterBtnstate) {
            button.setBackgroundColor(getColor(R.color.gray))
            categories.remove(string)
            enterBtnstate = false
        }
        else{
            button.setBackgroundColor(getColor(R.color.red))
            categories.add(string)
            enterBtnstate = true
        }

    }
    private fun updateCultBtnUI(button: Button, string: String) {
        if (cultureBtnstate) {
            button.setBackgroundColor(getColor(R.color.gray))
            categories.remove(string)
            cultureBtnstate = false
        }
        else{
            button.setBackgroundColor(getColor(R.color.red))
            categories.add(string)
            cultureBtnstate= true
        }

    }
    private fun updateSportsBtnUI(button: Button, string: String) {
        if (sportsBtnstate) {
            button.setBackgroundColor(getColor(R.color.gray))
            categories.remove(string)
            sportsBtnstate = false
        }
        else{
            button.setBackgroundColor(getColor(R.color.red))
            categories.add(string)
            sportsBtnstate = true
        }

    }
    private fun updateProgramBtnUI(button: Button, string: String) {
        if (programBtnstate) {
            button.setBackgroundColor(getColor(R.color.gray))
            categories.remove(string)
            programBtnstate = false
        }
        else{
            button.setBackgroundColor(getColor(R.color.red))
            categories.add(string)
            programBtnstate = true
        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        val foodState = foodBtnstate
        outState.putBoolean("saved", foodState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val userFoodBtn = savedInstanceState.getBoolean("saved")
        foodBtnstate = userFoodBtn
        updateFoodBtnUI(foodButton, "food")
    }
}