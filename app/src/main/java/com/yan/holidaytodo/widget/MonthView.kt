package com.yan.holidaytodo.widget


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.yan.holidaytodo.R
import com.yan.holidaytodo.adapter.CalendarAdapter


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      MonthView
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 17:57:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    月份
 */
class MonthView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayout(context,attrs, defStyleAttr, defStyleRes){

    companion object{
        //初始页面
        const val CURRENT_DAY_INDEX = 1000
        //当前界面
        var currentPosition = CURRENT_DAY_INDEX
    }

    private var viewPager2 : ViewPager2

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL
        View.inflate(getContext(),R.layout.calendar_pager,this)
        viewPager2 = findViewById(R.id.calendar_pager)
    }

    fun initAdapter(calendarAdapter: CalendarAdapter){
        viewPager2.adapter = calendarAdapter
        viewPager2.setCurrentItem(CURRENT_DAY_INDEX,false)
    }

}