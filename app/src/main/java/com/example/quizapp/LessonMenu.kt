package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import kotlin.math.roundToInt

class LessonMenu : AppCompatActivity() {

    private lateinit var progressBar : ProgressBar
    private var lessonName : String? = null

    private fun writeIntoStorage(){
        val inputStream : InputStream = assets.open("Stats.txt")
        val reader = BufferedReader(inputStream.reader())
        val content = StringBuilder()
        reader.use { usereader ->
            var line = usereader.readLine()
            while(line != null){
                line += "\n"
                content.append(line)
                line = usereader.readLine()
            }
        }
        content.setLength(content.length - 1)
        val fileOutputStream : FileOutputStream = openFileOutput("Stats.txt", Context.MODE_PRIVATE)
        val outputWriter = OutputStreamWriter(fileOutputStream)
        outputWriter.write(content.toString())
        outputWriter.close()
    }

    private fun updateProgress(){
        //functionality of progress bar
        var line : List<String>;
        val fileName = "Stats.txt"
        val file = File(filesDir.path + "/" + fileName)
        if(!file.exists()){
            writeIntoStorage()
        }

        var flashcardProgress = 0.0F
        var quizProgress = 0.0F

        val fileInputStream: FileInputStream? = openFileInput("Stats.txt")
        val inputStreamReader = InputStreamReader(fileInputStream)
        val reader = BufferedReader(inputStreamReader)
        reader.useLines { lines ->
            lines.forEach {
                line = it.split(";")
                if(line[0] == lessonName){
                    flashcardProgress = line[1].toFloat() / line[2].toFloat() * 100
                    quizProgress = line[3].toFloat() / line[4].toFloat() * 100
                }
            }
        }

        progressBar = findViewById(R.id.progress_bar)
        progressBar.progress = flashcardProgress.roundToInt()
        progressBar.secondaryProgress = quizProgress.roundToInt()
    }

    private fun resetProgress(){
        val fileName = "Stats.txt"
        val file = File(filesDir.path + "/" + fileName)
        if(file.exists()){
          file.delete()
        }
        updateProgress()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lesson_menu)

        val intent = intent.extras
        lessonName = intent!!.getString("lessonName")

        val title = findViewById<TextView>(R.id.lessonTitle)
        title.text = lessonName

        updateProgress()

        val cardButton = findViewById<Button>(R.id.cardButton)
        cardButton.setOnClickListener {
            startActivity(Intent(this, LearnActivity::class.java).putExtra("lessonName", lessonName))
        }

        val quizButton = findViewById<Button>(R.id.quizButton)
        quizButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java).putExtra("lessonName", lessonName))
        }

        val resetButton = findViewById<Button>(R.id.resetProgressButton)
        resetButton.setOnClickListener {
            resetProgress()
        }

    }

    override fun onResume() {
        super.onResume()
        updateProgress()
    }
}