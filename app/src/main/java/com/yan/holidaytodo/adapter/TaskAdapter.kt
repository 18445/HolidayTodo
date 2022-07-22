package com.yan.holidaytodo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.rv.TaskContent
import com.yan.holidaytodo.bean.rv.TaskTitle
import com.yan.holidaytodo.util.getWeek

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.adapter
 * @ClassName:      TaskAdapter
 * @Author:         Yan
 * @CreateDate:     2022年07月22日 02:39:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    任务Adapter
 */
class TaskAdapter (val context : Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        private const val TASK_TITLE = 0x000101
        private const val TASK_CONTEXT = 0x001010
    }
    //inflater
    private val inflater = LayoutInflater.from(context)
    //判断所在列表是否是可折叠的
    private val expandedItemsCache : HashMap<Int,Boolean> = hashMapOf()
    //判断列表是否是展开的
    var isExpanded = true
    //数据项
    private var mTotalItems = 0
    //总数据项
    private val mItems : MutableList<Any> = mutableListOf()
    //被展示的项
    private val mShownItems : MutableList<Any> = mutableListOf()

    inner class ViewHolderTitle(itemView: View) : RecyclerView.ViewHolder(itemView){
        //标题
        val taskTitle: TextView = itemView.findViewById(R.id.tv_task_title)
    }

    inner class ViewHolderContext(itemView: View) : RecyclerView.ViewHolder(itemView){
        //内容
        val taskImage = itemView.findViewById<ImageView>(R.id.iv_task_done)
        val taskDes = itemView.findViewById<TextView>(R.id.tv_task_done)
        val taskTitle = itemView.findViewById<TextView>(R.id.tv_task_content_title)
        val taskContent = itemView.findViewById<TextView>(R.id.tv_task_content_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == TASK_TITLE){
            val view = inflater.inflate(R.layout.item_task_title,parent,false)
            ViewHolderTitle(view)
        }else {
            val view = inflater.inflate(R.layout.item_task_content,parent,false)
            ViewHolderContext(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderTitle){
            //绑定Title
            val calendar = (mItems[position] as TaskTitle).calendarData
            val day = getWeek(calendar)
            holder.taskTitle.text = buildString {
                    append(calendar.year)
                    append("-")
                    append(calendar.month)
                    append("-")
                    append(calendar.day)
                    append(" ")
                    append(day)
                }
        }else if(holder is ViewHolderContext){
            //绑定context
            val taskInfo = (mItems[position] as TaskContent).taskInfo
            holder.taskTitle.text = taskInfo.title
            holder.taskContent.text = taskInfo.content
            holder.taskDes.text = if(taskInfo.status == 1){"已完成"}else{"未完成"}
            holder.taskImage.setImageResource(if (taskInfo.status == 1){R.drawable.ic_todo_list}else{R.drawable.ic_task_unfinish})
        }
    }

    override fun getItemCount(): Int {
        return mShownItems.size
    }

    override fun getItemViewType(position: Int): Int {

        if(mShownItems[position] is TaskTitle){
            return TASK_TITLE
        }else if (mShownItems[position] is TaskContent){
            return TASK_CONTEXT
        }

        return super.getItemViewType(position)
    }

    fun addObj(item : Any){
        mItems.add(item)
        mShownItems.add(item)
        expandedItemsCache[mTotalItems] = item is TaskTitle
        mTotalItems++
    }


    @SuppressLint("NotifyDataSetChanged")
    fun clearObj(){
        mItems.clear()
        mShownItems.clear()
        notifyDataSetChanged()
    }

    fun closeList(){
        var totalIndex = 0//每次被移除后index全部向前移一位
        for(i in 0 until expandedItemsCache.size){
            if(expandedItemsCache[i] == false){
                mShownItems.removeAt(i - totalIndex)
                notifyItemRemoved(i - totalIndex)
                totalIndex++
            }
        }
        isExpanded = false
    }

    fun openList(){
        for(i in 0 until expandedItemsCache.size){
            if(expandedItemsCache[i] == false){
                mShownItems.add(i,mItems[i])
                notifyItemChanged(i)
            }
        }
        isExpanded = true
    }
}