package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.InputStream

class LearnActivity : AppCompatActivity() {

    private var lessonName: String? = null
    private val listOfLines = mutableListOf<List<String>>()
    private val prevListOfLines = mutableListOf<List<String>>()
    private var totalFlashSize : Int = 0
    private var currentFlashcard : Int = 0

    private fun getData(){
        var intent = intent.extras
        lessonName = intent!!.getString("lessonName")

        val title = findViewById<TextView>(R.id.unitTitle)
        title.text = lessonName

        val fileName = lessonName!!.replace("\\s".toRegex(), "") + ".txt"

        var line : List<String>

        val inputStream : InputStream = assets.open(fileName)
        inputStream.reader().useLines { lines ->
            lines.forEach {
                line = it.split(";")
                listOfLines.add(line)
            }
        }
        listOfLines.shuffle()
        totalFlashSize = listOfLines.size
    }

    private fun showNextFlashcard(){
        currentFlashcard++
        val germanWord = findViewById<TextView>(R.id.germanWord)
        val germanTranslation = findViewById<TextView>(R.id.translation)
        val countFlash = findViewById<TextView>(R.id.countFlash)

        val fishu = listOfLines[0]
        germanWord.text = fishu[0]
        germanTranslation.text = fishu[1]
        countFlash.text = getString(R.string.count_flashcard_label, currentFlashcard, totalFlashSize)

        prevListOfLines.add(listOfLines[0])
        listOfLines.removeAt(0)
    }

// nie uzywac bo nie dziala jeszcze pls thx
    private fun ShowPrevFlashcard(){
        val germanWord = findViewById<TextView>(R.id.germanWord)
        val germanTranslation = findViewById<TextView>(R.id.translation)

        val fishu = prevListOfLines[0]
        germanWord.text = fishu[0]
        germanTranslation.text = fishu[1]

        listOfLines.add(0, prevListOfLines.last())
        prevListOfLines.removeLast()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flashcard)

        getData()
        showNextFlashcard()
    }

    fun nextButton(view: View) {
        if(listOfLines.size != 0){
            showNextFlashcard()
        }
        else{
            val intent = Intent(this, MenuActivity :: class.java)
            startActivity(intent)
        }

    }
}