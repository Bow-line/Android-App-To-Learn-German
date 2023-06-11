package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*

class LearnActivity : AppCompatActivity() {

    private var lessonName: String? = null

    //for the list of flashcards
    private val listOfLines = mutableListOf<List<String>>()
    private var listIndex : Int = 0
    private var countBackwards = 0

    private var totalFlashSize : Int = 0
    private var currentFlashcard : Int = 0

    private fun getData(){
        val intent = intent.extras
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

    private fun updateProgress(){
        //functionality of progress bar
        val fileName = "Stats.txt"

        val content = mutableListOf<MutableList<String>>()
        var line : MutableList<String>

        val fileInputStream: FileInputStream? = openFileInput(fileName)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val reader = BufferedReader(inputStreamReader)
        reader.useLines { lines ->
            lines.forEach {
                line = it.split(";") as MutableList<String>
                content.add(line)
            }
        }

        val newContent = mutableListOf<String>()
        var newString = ""

        for(x in content){
            if(x[0] == lessonName){
                var flashcardProgress = x[1].toInt()
                if(flashcardProgress < x[2].toInt())
                    flashcardProgress++
                x[1] = flashcardProgress.toString()
            }
            val newline : String = x[0] + ";" + x[1] + ";" + x[2] + ";" + x[3] + ";" + x[4]
            newString += newline
            if(x != content.last()){
                newString += '\n'
            }
            newContent.add(newline + '\n')
        }

        val fileOutputStream : FileOutputStream = openFileOutput("Stats.txt", Context.MODE_PRIVATE)
        val outputWriter = OutputStreamWriter(fileOutputStream)
        outputWriter.write(newString)
        outputWriter.close()
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
                if (countBackwards == 0){
                    updateProgress()
                }
                else{
                    countBackwards--
                }
                showNextFlashcard()
            }
            else{
                val intent = Intent(this, MenuActivity :: class.java)
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.prevBtn).setOnClickListener{
            showPrevFlashcard()
            countBackwards++
        }
    }

}