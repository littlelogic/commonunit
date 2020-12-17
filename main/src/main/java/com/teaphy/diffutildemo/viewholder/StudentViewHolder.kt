package com.teaphy.diffutildemo.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.badlogic.ui.R
import kotlinx.android.synthetic.main.differ_item_student.view.*

/**
 * @desc
 * @author Tiany
 * @date  2018/4/4 0004
 */
class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvNoValue = itemView.findViewById<TextView>(R.id.tvNoValue)
    val tvNameValue = itemView.findViewById<TextView>(R.id.tvNameValue)
    val tvRankValue = itemView.findViewById<TextView>(R.id.tvRankValue)
}