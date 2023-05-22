package com.example.quizapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(clickListener: ClickListener) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var cardModelList: List<CardModel> = arrayListOf();
    private lateinit var context: Context;
    private var clickListener : ClickListener = clickListener

    public fun setData(cardModel : List<CardModel>){
        cardModelList = cardModel
        notifyDataSetChanged()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.menu_icon)
        var title = itemView.findViewById<TextView>(R.id.menu_icon_label)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context;

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_item, parent, false))
    }

    override fun getItemCount(): Int {
        return cardModelList.size
    }

    interface ClickListener{
        fun clickedItem(cardModel: CardModel);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var cardModel = cardModelList.get(position);
        var img = cardModel.img
        var title = cardModel.title

        holder.img.setImageDrawable(ContextCompat.getDrawable(context, img))
        holder.title.text = title
        holder.itemView.setOnClickListener{
            clickListener.clickedItem(cardModel)
        }
    }

}