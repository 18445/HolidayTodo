package com.yan.holidaytodo.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yan.holidaytodo.base.BaseActivity
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.State
import com.yan.holidaytodo.bean.view.CalendarAttr
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.ui.fragment.CalendarFragment
import com.yan.holidaytodo.ui.fragment.TodoFragment
import com.yan.holidaytodo.ui.viewmodel.HomeViewModel
import com.yan.holidaytodo.util.*
import com.yan.holidaytodo.widget.CalendarWeekView
import com.yan.holidaytodo.widget.CustomDayView
import com.yan.holidaytodo.widget.MonthView


class MainActivity : BaseActivity<HomeViewModel>() {

    //TabLayout是否显示
    private var tabIsShown= false

    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: Toolbar
    private lateinit var viewPager2: ViewPager2

    private val mFragments : List<Fragment> by lazy {
        mutableListOf(CalendarFragment(),TodoFragment())
    }
    private val tabTitle : List<String> = mutableListOf("日历","日程")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTabLayout()
        initToolbar()
        initVp2()
//        val calendarWeekView = findViewById<CalendarWeekView>(R.id.calendar_week).apply {
//            initOnSelectListener(object : OnSelectDateListener{
//
//                override fun onSelectDate(
//                    calendarData: CalendarData,
//                    row: Int,
//                    col: Int,
//                    type: CalendarAttr.CalendarType,
//                    state: State,
//                ) {
//                }
//
//                override fun onSelectOtherMonth(offset: Int) {
//                }
//
//            })
//            initDayDrawer(CustomDayView(context,R.layout.custiom_day))
//        }
//
//        findViewById<MonthView>(R.id.month_view).apply {
//            initAdapter(onSelectDateListener = object : OnSelectDateListener{
//                override fun onSelectDate(
//                    calendarData: CalendarData,
//                    row: Int,
//                    col: Int,
//                    type: CalendarAttr.CalendarType,
//                    state: State,
//                ) {
//                    viewModel.onSelectedDate(calendarData)
//                }
//
//                override fun onSelectOtherMonth(offset: Int) {
//
//                }
//
//            }, onSelectedDateHide = {
//                calendarWeekView.isVisible = it
//            }, calendarWeekView, CustomDayView(baseContext, R.layout.custiom_day))
//        }

    }

    private fun initTabLayout(){
        tabLayout = findViewById(R.id.tl_main)
    }

    private fun initToolbar(){
        //初始化Toolbar相关的事件
        toolbar = findViewById<Toolbar>(R.id.tb_main).apply {
            viewModel.onSelectedDate(CalendarData(getYear(), getMonth(), getDay()))
            setOnClickListener {
                if(tabIsShown){
                    moveUpSmooth(tabLayout,125,275)
                    this@MainActivity.tabIsShown = false
                }else{
                    moveDownSmooth(tabLayout,125,275)
                    this@MainActivity.tabIsShown = true
                }
            }
            viewModel.todayDate.observe(this@MainActivity){
                title = it
            }
        }
        viewModel.onSelectedDate(CalendarData(getYear(), getMonth(), getDay()))
    }

    private fun initVp2(){
        //初始化ViewPager2
        viewPager2 = findViewById<ViewPager2>(R.id.vp_main).apply {
            adapter = object : FragmentStateAdapter(this@MainActivity){
                override fun getItemCount(): Int {
                    return mFragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return mFragments[position]
                }
            }
        }
        TabLayoutMediator(tabLayout,viewPager2,true
        ) { tab, position -> tab.apply {
            text = tabTitle[position]
            view.isLongClickable = false
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                view.tooltipText = ""
            }
        } }.attach()
    }
}