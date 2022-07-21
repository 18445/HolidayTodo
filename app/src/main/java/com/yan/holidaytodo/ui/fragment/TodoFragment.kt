package com.yan.holidaytodo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.R
import com.yan.holidaytodo.adapter.TaskAdapter
import com.yan.holidaytodo.base.BaseFragment
import com.yan.holidaytodo.bean.net.wanandroid.TaskInfo
import com.yan.holidaytodo.bean.rv.TaskContent
import com.yan.holidaytodo.bean.rv.TaskTitle
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.util.getDay
import com.yan.holidaytodo.util.getMonth
import com.yan.holidaytodo.util.getYear

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.ui.fragment
 * @ClassName:      TodoFragment
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 19:55:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    任务Fragment
 */
class TodoFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_todo,container,false)

    private lateinit var mRecyclerView : RecyclerView

    //空数据时加载的图片和文字
    private lateinit var mEmptyImage : ImageView
    private lateinit var mEmptyText : TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initRecyclerView(view)
    }

    private fun initView(view : View){
        mEmptyImage = view.findViewById<ImageView?>(R.id.iv_task_none).apply {
            isVisible = false
        }
        mEmptyText = view.findViewById<TextView?>(R.id.tv_task_none).apply {
            isVisible = false
        }
    }

    private fun initRecyclerView(view : View){
        mRecyclerView = view.findViewById<RecyclerView?>(R.id.rv_task)
        val adapter = TaskAdapter(requireContext()).apply {
            val taskInfo = TaskInfo("",",",",",1,"",1,0,0,"",0,0)
            addObj(TaskTitle(CalendarData(getYear(), getMonth(), 10)))
            addObj(TaskContent(taskInfo))
            addObj(TaskContent(taskInfo))
            addObj(TaskTitle(CalendarData(getYear(), getMonth(), 11)))
            addObj(TaskContent(taskInfo))
            addObj(TaskContent(taskInfo))
            addObj(TaskTitle(CalendarData(getYear(), getMonth(), 12)))
            addObj(TaskContent(taskInfo))
            addObj(TaskContent(taskInfo))
            addObj(TaskContent(taskInfo))
        }
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

}