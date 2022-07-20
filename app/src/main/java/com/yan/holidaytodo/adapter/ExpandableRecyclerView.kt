package com.yan.holidaytodo.adapter

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.size
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.rv.ItemContext
import com.yan.holidaytodo.bean.rv.ItemTitle
import okhttp3.internal.notify
import java.util.zip.Inflater

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.adapter
 * @ClassName:      ExpandableRecyclerView
 * @Author:         Yan
 * @CreateDate:     2022年07月20日 14:22:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    可折叠的Recycler列表
 */
class ExpandableRecyclerView(val context : Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        private const val ITEM_TITLE = 0x00001
        private const val ITEM_CONTEXT = 0x00010
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
        val itemTitle : TextView = itemView.findViewById(R.id.tv_todo_title)
    }

    inner class ViewHolderContext(itemView: View) : RecyclerView.ViewHolder(itemView){
        //内容
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ITEM_TITLE){
            val view = inflater.inflate(R.layout.item_todo_title,parent,false)
            ViewHolderTitle(view)
        }else {
            val view = inflater.inflate(R.layout.item_todo,parent,false)
            ViewHolderContext(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderTitle){
            //绑定Title
            holder.itemTitle.text =(mShownItems[position] as ItemTitle).title
        }else if(holder is ViewHolderContext){
            //绑定context

        }
    }

    override fun getItemCount(): Int {
        return mShownItems.size
    }

    override fun getItemViewType(position: Int): Int {

        if(mShownItems[position] is ItemTitle){
            return ITEM_TITLE
        }else if (mShownItems[position] is ItemContext){
            return ITEM_CONTEXT
        }

        return super.getItemViewType(position)
    }

    fun addObj(item : Any){
        mItems.add(item)
        mShownItems.add(item)
        expandedItemsCache[mTotalItems] = item is ItemTitle
        mTotalItems++
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