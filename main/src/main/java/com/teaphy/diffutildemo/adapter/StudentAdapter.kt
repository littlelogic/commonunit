package com.teaphy.diffutildemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.badlogic.ui.R
import com.teaphy.diffutildemo.bean.StudentBean
import com.teaphy.diffutildemo.callback.StudentCallback
import com.teaphy.diffutildemo.viewholder.StudentViewHolder

/**
 * @desc
 * @author Tiany
 * @date  2018/4/4 0004
 */
class StudentAdapter : RecyclerView.Adapter<StudentViewHolder>() {

    val mCallback = StudentCallback(this)

    val mSortedList = SortedList<StudentBean>(StudentBean::class.java, SortedList.BatchedCallback<StudentBean>(mCallback))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.differ_item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mSortedList.size()
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val bean = mSortedList.get(position)

        holder.tvNoValue.text = bean.no.toString()
        holder.tvNameValue.text = bean.name
        holder.tvRankValue.text = bean.rank.toString()
    }
}