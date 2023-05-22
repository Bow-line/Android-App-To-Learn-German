package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TestActivity : AppCompatActivity() {

    private lateinit var selectedLesson : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initData()
    }

    private fun initData(){
        selectedLesson = findViewById(R.id.lessonName)
        getData()
    }

    private fun getData(){
        var intent = intent.extras

        var lessonName = intent!!.getString("lessonName")

        selectedLesson.text = lessonName
    }
}