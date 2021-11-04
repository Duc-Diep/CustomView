package com.ducdiep.excustomview.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.ducdiep.excustomview.R
import kotlinx.android.synthetic.main.color_item.view.*

class ColorAdapter(var context: Context, var listColor:List<String>):RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {
    var onClick:((String)->Unit)? = null

    fun setOnClickItem(callBack:(String)->Unit){
        onClick = callBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.color_item,parent,false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        var color = listColor[position]
        holder.viewColor.setBackgroundColor(Color.parseColor(color))
        holder.itemView.setOnClickListener{
            onClick?.invoke(color)
        }
    }

    override fun getItemCount(): Int {
        return listColor.size
    }
    class ColorViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var viewColor = itemView.findViewById<RelativeLayout>(R.id.color_item)
    }
}