package com.teaphy.diffutildemo.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.badlogic.ui.R

/**
 * @desc
 * @author Tiany
 * @date  2018/4/2 0002
 */
class DiffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.tvName)
    val tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)
}