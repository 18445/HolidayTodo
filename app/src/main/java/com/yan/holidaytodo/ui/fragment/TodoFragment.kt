package com.yan.holidaytodo.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yan.holidaytodo.R
import com.yan.holidaytodo.adapter.TaskAdapter
import com.yan.holidaytodo.base.BaseFragment
import com.yan.holidaytodo.bean.net.wanandroid.TaskInfo
import com.yan.holidaytodo.bean.rv.TaskContent
import com.yan.holidaytodo.bean.rv.TaskTitle
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.ui.viewmodel.TaskViewModel
import com.yan.holidaytodo.util.getDay
import com.yan.holidaytodo.util.getMonth
import com.yan.holidaytodo.util.getYear
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.timerTask

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

    private val viewModel by activityViewModels<TaskViewModel>()

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter: TaskAdapter

    //空数据时加载的图片和文字
    private lateinit var mEmptyImage : ImageView
    private lateinit var mEmptyText : TextView

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    private val mAllTasks = mutableListOf<TaskInfo>()
    private val readyToAddTasks : HashMap<String,MutableList<TaskInfo>> = HashMap()
    private var mTaskOver = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initRecyclerView(view)
        observeItems()
        initQueryAllTasks(1)
    }

    private fun initView(view : View){
        mEmptyImage = view.findViewById<ImageView?>(R.id.iv_task_none).apply {
            isVisible = false
        }
        mEmptyText = view.findViewById<TextView?>(R.id.tv_task_none).apply {
            isVisible = false
        }
        mSwipeRefreshLayout = view.findViewById<SwipeRefreshLayout?>(R.id.sfl_task).apply {
            setColorSchemeColors(Color.parseColor("#FF4081"))
            setOnRefreshListener {
                initQueryAllTasks(1)
            }
        }
    }

    private fun initRecyclerView(view : View){
        mRecyclerView = view.findViewById(R.id.rv_task)
        mAdapter = TaskAdapter(requireContext()) { viewModel.finishTodo(it,1) }
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initQueryAllTasks(index : Int){
        viewModel.queryAllTodoList(index)
    }

    private fun observeItems(){
        viewModel.observeAllTasks(this){
            onSuccess {
                mTaskOver = it.over
                mAllTasks.clear()
                readyToAddTasks.clear()
                for (info in it.datas){
                    mAllTasks.add(info)
                }
                requestDone()
                mEmptyText.isVisible = false
                mEmptyImage.isVisible = false
            }
            onEmpty {
                mEmptyImage.isVisible = true
                mEmptyText.isVisible = true
            }
            onComplete {
                mSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun requestDone(){
        resetTask()
        addToRecyclerView()
    }

    private fun resetTask(){
        for (task in mAllTasks){
            val tasks = readyToAddTasks[task.dateStr]
            if(tasks == null){
                readyToAddTasks[task.dateStr] = mutableListOf()
            }
            readyToAddTasks[task.dateStr]!!.add(task)
        }
    }

    private fun addToRecyclerView(){
        mAdapter.clearObj()
        var total = 0
        //降序排列
        val tempMap = readyToAddTasks.toSortedMap(compareByDescending{
            it
        })
        for(date in tempMap){
            val dateArray = date.key.split("-")
            mAdapter.addObj(TaskTitle(CalendarData(dateArray[0].toInt(),dateArray[1].toInt(),dateArray[2].toInt())))
            total++
            for(task in date.value){
                mAdapter.addObj(TaskContent(task))
                total++
            }
        }
        mAdapter.notifyItemRangeInserted(0,total)
    }



}