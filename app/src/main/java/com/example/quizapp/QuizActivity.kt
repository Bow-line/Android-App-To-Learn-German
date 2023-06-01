package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.databinding.ActivityMainBinding

class QuizActivity : AppCompatActivity() {
    val quizData: MutableList<MutableList<String>> = mutableListOf()
    private var lessonName: String? = null

    private lateinit var binding: ActivityMainBinding
    private var rightAnswer: String? = null
    private var rightAnswrCount = 0
    private var quizCount = 1
    private var QUIZ_COUNT = 0



    private fun getData(){

        var intent = intent.extras
        lessonName = intent!!.getString("lessonName")

        val title = findViewById<TextView>(R.id.unitTitle)

        val fileName = lessonName!!.replace("\\s".toRegex(), "") + "-quiz.txt"

        assets.open(fileName).bufferedReader().use { reader ->
            reader.forEachLine { line ->
                val lineData = line.split(";").toMutableList()
                quizData.add(lineData)
            }
        }


        QUIZ_COUNT = quizData.size


    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getData()
        showNextQuiz()
    }

    fun showNextQuiz(){
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        val quiz = quizData[0]

        binding.questionLabel.text = quiz[0]
        rightAnswer = quiz[1]

        quiz.removeAt(0)
        quiz.shuffle()

        binding.answerBtn1.text = quiz[0]
        binding.answerBtn2.text = quiz[1]
        binding.answerBtn3.text = quiz[2]
        binding.answerBtn4.text = quiz[3]
        quizData.removeAt(0)
    }

    fun checkAnswer(view: View){
        val answerBtn: Button = findViewById(view.id)
        val btnText = answerBtn.text.toString()

        val alertTitle: String
        if (btnText == rightAnswer){
            alertTitle="Dobrze!"
            rightAnswrCount++
        }
        else {
            alertTitle = "Źle..."
        }

        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("Odpowiedź: $rightAnswer")
            .setPositiveButton("OK"){
                    dialogInterface,i -> checkQuizCount()
            }
            .setCancelable(false)
            .show()
    }
    fun checkQuizCount(){
        if( quizCount == QUIZ_COUNT){
            val intent = Intent(this@QuizActivity, ResultActivity::class.java)
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswrCount)
            startActivity(intent)
        }
        else{
            quizCount++
            showNextQuiz()
        }
    }
}