package com.yan.holidaytodo.widget


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.yan.holidaytodo.R
import com.yan.holidaytodo.adapter.CalendarAdapter
import com.yan.holidaytodo.bean.CalendarAttr
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.bean.Day
import com.yan.holidaytodo.bean.State
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnCalendarStateListener
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.helper.CalendarMover


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
class MonthView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        //初始页面
        const val CURRENT_DAY_INDEX = 1000

        //当前界面
        var currentPosition = CURRENT_DAY_INDEX
    }

    private var viewPager2: ViewPager2

    private lateinit var mCalendarWeekView: CalendarWeekView

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL
        View.inflate(getContext(), R.layout.calendar_pager, this)
        viewPager2 = findViewById<ViewPager2?>(R.id.calendar_pager).apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    currentPosition = position
                    if (this@MonthView::mCalendarWeekView.isInitialized){
                        mCalendarWeekView.changePosition(position,false)
                    }
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (this@MonthView::mCalendarWeekView.isInitialized){
                        mCalendarWeekView.isVisible = false
                    }
                }
            })
        }
    }

    fun initAdapter(context: Context,onSelectedDateHide : (Boolean)->Unit, calendarWeekView: CalendarWeekView, iDayDrawer: IDayDrawer) {
        mCalendarWeekView = calendarWeekView
        viewPager2.adapter = CalendarAdapter(context, object : OnSelectDateListener {

            override fun onSelectOtherMonth(offset: Int) {
                viewPager2.currentItem = currentPosition + offset
                mCalendarWeekView.changePosition(currentPosition,true)
            }

            override fun onSelectDate(calendarData: CalendarData,row : Int,col : Int,type : CalendarAttr.CalendarType, state:State) {
                calendarWeekView.onClickItem(calendarData,row,col,type,state)
                calendarWeekView.changeSelectedData(calendarData)
            }

        }, object : OnCalendarStateListener {
            override fun onFoldingState() {
                isAllowSlide(false)
            }

            override fun onStretchingState() {
                isAllowSlide(false)
            }

            override fun onNormalState() {
                isAllowSlide(true)
            }

        }, onSelectedDateHide, calendarWeekView, iDayDrawer)
        viewPager2.setCurrentItem(CURRENT_DAY_INDEX, false)
    }

    fun isAllowSlide(state: Boolean) {
        viewPager2.isUserInputEnabled = state
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }



}