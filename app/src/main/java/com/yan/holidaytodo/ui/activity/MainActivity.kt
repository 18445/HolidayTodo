package com.yan.holidaytodo.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yan.holidaytodo.base.BaseActivity
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.Session
import com.yan.holidaytodo.bean.State
import com.yan.holidaytodo.bean.view.CalendarAttr
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.ui.fragment.CalendarFragment
import com.yan.holidaytodo.ui.fragment.TaskBottomSheetDialog
import com.yan.holidaytodo.ui.fragment.TimerFragment
import com.yan.holidaytodo.ui.fragment.TodoFragment
import com.yan.holidaytodo.ui.viewmodel.HomeViewModel
import com.yan.holidaytodo.util.*
import com.yan.holidaytodo.widget.CalendarWeekView
import com.yan.holidaytodo.widget.CustomDayView
import com.yan.holidaytodo.widget.MonthView


class MainActivity : BaseActivity<HomeViewModel>() {

    //TabLayout是否显示
    private var tabIsShown= true

    private val windowInsetsController by lazy {
        ViewCompat.getWindowInsetsController(window.decorView)
    }

    private lateinit var container : ConstraintLayout
    private lateinit var tabLayout : TabLayout
    private lateinit var toolbar : Toolbar
    private lateinit var viewPager2 : ViewPager2
    private lateinit var todoButton : FloatingActionButton
    private lateinit var backButton : FloatingActionButton
    private var mSelectedDate : CalendarData = CalendarData(getYear(), getMonth(), getDay())

    private val mFragments : List<Fragment> by lazy {
        mutableListOf(TimerFragment(),CalendarFragment(),TodoFragment())
    }
    private val tabTitle : List<String> = mutableListOf("任务","日历","日程")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTabLayout()
        initToolbar()
        initVp2()
        initButton()
    }

    private fun initTabLayout(){
        container = findViewById(R.id.csl_main_container)
        tabLayout = findViewById(R.id.tl_main)
    }

    private fun initToolbar(){
        //初始化Toolbar相关的事件
        toolbar = findViewById<Toolbar>(R.id.tb_main).apply {
            moveDownSmooth(tabLayout,125,100)
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
            setPageTransformer(ViewPagerScale())
        }
        TabLayoutMediator(tabLayout,viewPager2,true
        ) { tab, position -> tab.apply {
            text = tabTitle[position]
            view.isLongClickable = false
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                view.tooltipText = ""
            }
        } }.attach()
        viewPager2.setCurrentItem(1,false)
    }

    private fun initButton(){
        todoButton = findViewById<FloatingActionButton>(R.id.fa_btn_todo).apply {
            setOnClickListener {
                if(!Session.state){ //跳转登录界面
                    startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                    return@setOnClickListener
                }
                val bottomSheetDialog = TaskBottomSheetDialog()
                bottomSheetDialog.show(supportFragmentManager,"TaskBottomSheetDialog")
            }
        }
        backButton = findViewById<FloatingActionButton>(R.id.fa_btn_back).apply {
            (mFragments[1] as CalendarFragment).setOnDateListener { //设置隐藏时间
                isVisible = it.day != getDay() || it.month != getMonth() || it.year != getYear()
                mSelectedDate = it
            }

            setOnClickListener {
                viewPager2.currentItem = 1
                (mFragments[1] as CalendarFragment).backToday()
            }
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 2){
                    backButton.isVisible = true
                }else if(position == 1){
                    if(mSelectedDate.day == getDay()
                        && mSelectedDate.month == getMonth()
                        && mSelectedDate.year == getYear()){
                        backButton.isVisible = false
                    }
                }
                if(position == 0){
                    backButton.isVisible = false
                    if(this@MainActivity::toolbar.isInitialized){
                        toolbar.setBackgroundColor(Color.BLACK)
                        toolbar.setTitleTextColor(Color.WHITE)
                    }
                    tabLayout.setBackgroundColor(Color.BLACK)
                    container.setBackgroundColor(Color.BLACK)
                    todoButton.isVisible = false
                    windowInsetsController?.isAppearanceLightStatusBars = false

                    tabLayout.setTabTextColors(Color.WHITE,Color.LTGRAY)
                    tabLayout.setSelectedTabIndicatorColor(Color.DKGRAY)

                }else{
                    if(this@MainActivity::toolbar.isInitialized){
                        toolbar.setBackgroundColor(Color.WHITE)
                    }
                    tabLayout.setBackgroundColor(Color.WHITE)
                    container.setBackgroundColor(Color.WHITE)
                    toolbar.setTitleTextColor(Color.BLACK)
                    todoButton.isVisible = true
                    windowInsetsController?.isAppearanceLightStatusBars = true

                    tabLayout.setTabTextColors(Color.parseColor("#2980b9"),Color.parseColor("#70a1ff"))

                }
            }
        })
    }

    /*
    private var mLastY = 0
    private var mDiffY = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                mLastY = event.y.toInt()
                return true
            }
            MotionEvent.ACTION_MOVE ->{
                mDiffY = event.y.toInt() - mLastY
                Log.e("mDiffy",mDiffY.toString())
                mLastY = event.y.toInt()
                if(mDiffY > 0){
                    todoButton.isVisible = false
                    backButton.isVisible = false
                }
            }
        }

        return super.onTouchEvent(event)
    }
    */

}