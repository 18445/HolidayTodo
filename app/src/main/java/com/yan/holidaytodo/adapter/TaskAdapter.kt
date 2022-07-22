package com.yan.holidaytodo.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.net.wanandroid.TaskInfo
import com.yan.holidaytodo.bean.rv.TaskContent
import com.yan.holidaytodo.bean.rv.TaskTitle
import com.yan.holidaytodo.ui.fragment.CardDialog
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
class TaskAdapter (val context : Context,val onTaskFinish: (taskInfo : TaskInfo) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        private const val TASK_TITLE = 0x000101
        private const val TASK_CONTEXT = 0x001010
    }
    //inflater
    private val inflater = LayoutInflater.from(context)
    //判断所在列表是否是可折叠的
    private val expandedItemsCache : HashMap<Int,Boolean> = hashMapOf()
    //判断某一可折叠的位置是否已经折叠了
    private val hasExpandedItemCache : HashMap<Int,Boolean> = hashMapOf()
    //在第几个可折叠项前有几项已经被折叠
    private val mExpandedItems : HashMap<Int,Int> = hashMapOf()
    //数据项
    private var mTotalItems = 0
    //可折叠的项
    private var mFoldItems = 0
    //总数据项
    private val mItems : MutableList<Any> = mutableListOf()
    //被展示的项
    private val mShownItems : MutableList<Any> = mutableListOf()

    inner class ViewHolderTitle(itemView: View) : RecyclerView.ViewHolder(itemView){
        //标题
        val taskTitle : TextView = itemView.findViewById<TextView?>(R.id.tv_task_title)
        val taskExpand : ImageView = itemView.findViewById<ImageView>(R.id.iv_task_expand).apply {
            setOnClickListener {
                if (hasExpandedItemCache[absoluteAdapterPosition] == false){
                    closeList(absoluteAdapterPosition)
                    hasExpandedItemCache[absoluteAdapterPosition] = true
                }else{
                    openList(absoluteAdapterPosition)
                    hasExpandedItemCache[absoluteAdapterPosition] = false
                }
            }
        }
    }

    inner class ViewHolderContext(itemView: View) : RecyclerView.ViewHolder(itemView){
        //内容
        val taskImage : ImageView = itemView.findViewById<ImageView>(R.id.iv_task_done).apply {
            setOnClickListener {
                val dialog = CardDialog(context)
                dialog.show()
            }
        }
        val taskDes: TextView = itemView.findViewById(R.id.tv_task_done)
        val taskTitle : TextView= itemView.findViewById(R.id.tv_task_content_title)
        val taskContent : TextView= itemView.findViewById(R.id.tv_task_content_content)
        val taskDone : ImageView= itemView.findViewById<ImageView>(R.id.iv_task_finish).apply {
            setOnClickListener {
                createDeleteDialog((mItems[absoluteAdapterPosition] as TaskContent).taskInfo,it)
            }
        }
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
            val calendar = (mShownItems[position] as TaskTitle).calendarData
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
            val taskInfo = (mShownItems[position] as TaskContent).taskInfo
            holder.taskTitle.text = taskInfo.title
            holder.taskContent.text = taskInfo.content
            holder.taskDes.text = if(taskInfo.status == 1){"已完成"}else{"未完成"}
            holder.taskImage.setImageResource(if (taskInfo.status == 1){R.drawable.ic_todo_list}else{R.drawable.ic_task_unfinish})
            holder.taskDone.isVisible = taskInfo.status == 0
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

    private fun createDeleteDialog(taskInfo : TaskInfo,view : View){
        val alertDialog: AlertDialog = context.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("确定"
                ) { dialog, id ->
                    onTaskFinish(taskInfo)
                    view.isVisible = false
                }
                setNegativeButton("取消"
                ) { dialog, id ->
                    // User cancelled the dialog
                }
                setTitle("完成待办事项")
                setMessage("是否确定完成该待办事项: ${taskInfo.title}")
            }
            builder.create()
        }
        alertDialog.show()
    }



    fun addObj(item : Any){
        mItems.add(item)
        mShownItems.add(item)
        hasExpandedItemCache[mTotalItems] = false
        expandedItemsCache[mTotalItems] = item is TaskTitle
        if(item is TaskTitle){
            mExpandedItems[mFoldItems] = 0
        }
        mFoldItems++
        mTotalItems++
    }


    @SuppressLint("NotifyDataSetChanged")
    fun clearObj(){
        mItems.clear()
        mShownItems.clear()
        notifyDataSetChanged()
    }

    private fun closeList(absPosition : Int){
        var firstItem = -1
        var endItem = -1
        for(i in absPosition until mShownItems.size){
            if(mShownItems[i] is TaskTitle){
                firstItem = i
                break
            }
        }
        for (i in firstItem+1 until mShownItems.size){
            if(mShownItems[i] is TaskTitle){
                endItem = i
                break
            }
        }
        if (firstItem == -1){
            return
        }
        if( endItem == -1 ){
            endItem = mShownItems.size
        }
        for((totalIndex, i) in (firstItem + 1 until endItem).withIndex()){
            mShownItems.removeAt(i - totalIndex)
            notifyItemRemoved(i - totalIndex)
        }

//        resetCouldBeFold()
//
//        //被折叠的第几个Title
//        val titleItem = calWhichItem(absPosition)
//        //前面折叠的项 absPosition - mNoFold -> 第几个可折叠项
//        val mBeFolded = mExpandedItems[titleItem]
//
//        var totalIndex = 0
//        val index = mBeFolded + absPosition
//        if(index > expandedItemsCache.size){
//            return
//        }
//        var firstItem = -1
//        var endItem = -1
//        for(i in index until expandedItemsCache.size){
//            if(expandedItemsCache[i] == true){
//                firstItem = i
//                break
//            }
//        }
//        for (i in firstItem+1 until expandedItemsCache.size){
//            if(expandedItemsCache[i] == true){
//                endItem = i
//                break
//            }
//        }
//        if (firstItem == -1){
//            return
//        }
//        if( endItem == -1 ){
//            endItem = expandedItemsCache.size
//        }
//        for( i in firstItem + 1 until endItem ){
//            mShownItems.removeAt(i - totalIndex - mBeFolded)
//            notifyItemRemoved(i - totalIndex - mBeFolded)
//            totalIndex++
//        }
//        mExpandedItems[titleItem] = totalIndex

    }

    private fun openList(absPosition : Int){
        //之前折叠的距离
        var expandItems = 0

        for(i in 0 until absPosition ){
            if (hasExpandedItemCache[i] == true){//已经折叠
                //记录距离
                var dis = 0
                for(j in i + 1 + expandItems until mTotalItems){//找到下一个折叠点
                    if(expandedItemsCache[j] == true){
                        break
                    }
                    dis++
                }
                expandItems += dis
            }
        }

        if (absPosition > mShownItems.size) {
            return
        }

        var firstItem = -1
        var endItem = -1
        for (i in absPosition until mShownItems.size) {
            if (mShownItems[i] is TaskTitle) {
                firstItem = i
                break
            }
        }
        for (i in firstItem + 1 + expandItems until mTotalItems){
            if(expandedItemsCache[i] == true){
                endItem = i - expandItems
                break
            }
        }
        if( firstItem == -1 ){
            return
        }
        if( endItem == -1 ){
            if(firstItem + 1 + expandItems >= mTotalItems || firstItem + 1 +expandItems < 0){
                return
            }
            for(i in firstItem+1+expandItems until mTotalItems){
                mShownItems.add(i-expandItems,mItems[i])
                notifyItemChanged(i-expandItems)
            }
            return
        }
        for( i in firstItem+1 until endItem){
            mShownItems.add(i,mItems[i + expandItems])
            notifyItemChanged(i)
        }
    }

}