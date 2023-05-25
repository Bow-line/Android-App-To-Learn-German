package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class LearnActivity : AppCompatActivity() {

    private var lessonName: String? = null

    //for the list of flashcards
    private val listOfLines = mutableListOf<List<String>>()
    private var listIndex : Int = 0


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

        val flashcard = listOfLines[listIndex]
        germanWord.text = flashcard[0]
        germanTranslation.text = flashcard[1]
        countFlash.text = getString(R.string.count_flashcard_label, currentFlashcard, totalFlashSize)
    }

    private fun showPrevFlashcard(){
        if (listIndex > 0){
            val germanWord = findViewById<TextView>(R.id.germanWord)
            val germanTranslation = findViewById<TextView>(R.id.translation)
            val countFlash = findViewById<TextView>(R.id.countFlash)

            currentFlashcard--
            listIndex--

            val flashcard = listOfLines[listIndex]
            germanWord.text = flashcard[0]
            germanTranslation.text = flashcard[1]
            countFlash.text = getString(R.string.count_flashcard_label, currentFlashcard, totalFlashSize)

        }
        else {
            Toast.makeText(applicationContext, "Nie ma poprzedniej fiszki!", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flashcard)

        getData()
        showNextFlashcard()

        // implementing functionality of clicking the next button
        findViewById<Button>(R.id.nextBtn).setOnClickListener{
            if(listIndex+1 < totalFlashSize){
                listIndex++
                showNextFlashcard()
            }
            else{
                val intent = Intent(this, MenuActivity :: class.java)
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.prevBtn).setOnClickListener{
            showPrevFlashcard()
        }
    }

}