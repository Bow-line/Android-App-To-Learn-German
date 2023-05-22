package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LessonMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lesson_menu)

        var intent = intent.extras
        var lessonName = intent!!.getString("lessonName")

        val title = findViewById(R.id.lessonTitle) as TextView
        title.text = lessonName

        val cardButton = findViewById(R.id.cardButton) as Button
        cardButton.setOnClickListener {
            startActivity(Intent(this, LearnActivity::class.java).putExtra("lessonName", lessonName))
        }

        val quizButton = findViewById(R.id.quizButton) as Button
        quizButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java).putExtra("lessonName", lessonName))
        }
    }
}