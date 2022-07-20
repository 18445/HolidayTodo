package com.yan.holidaytodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.R

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.adapter
 * @ClassName:      TodoAdapter
 * @Author:         Yan
 * @CreateDate:     2022年07月16日 01:02:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    Todo清单的列表
 */
class TodoAdapter(val context: Context) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }


}