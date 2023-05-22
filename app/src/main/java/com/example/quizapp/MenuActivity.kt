package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuActivity : AppCompatActivity(), MenuAdapter.ClickListener {

    private lateinit var rvMenu : RecyclerView
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initData()
    }

    private fun initData(){
        rvMenu = findViewById(R.id.rv_menu)
        initRv()
    }

    private fun initCards():List<CardModel>{
        var cardList = ArrayList<CardModel>()
        cardList.add(CardModel(R.drawable.first_words, "Podstawowe zwroty"))
        cardList.add(CardModel(R.drawable.apple, "Jedzenie"))
        cardList.add(CardModel(R.drawable.snow, "Pogoda"))
        cardList.add(CardModel(R.drawable.travel, "Podróże"))
        cardList.add(CardModel(R.drawable.heart, "Uczucia"))
        cardList.add(CardModel(R.drawable.clothes, "Wygląd"))
        cardList.add(CardModel(R.drawable.animal, "Zwierzęta"))
        cardList.add(CardModel(R.drawable.technology, "Technologia"))

        return cardList
    }

    private fun initRv(){
        rvMenu.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        menuAdapter = MenuAdapter(this)
        rvMenu.adapter = menuAdapter
        showData()
    }

    private fun showData(){
        menuAdapter.setData(initCards())
    }

    override fun clickedItem(cardModel: CardModel) {
        startActivity(Intent(this, LessonMenu::class.java).putExtra("lessonName", cardModel.title))
    }

}