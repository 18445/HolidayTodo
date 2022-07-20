package com.yan.holidaytodo.widget


import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.yan.holidaytodo.R
import com.yan.holidaytodo.adapter.CalendarAdapter
import com.yan.holidaytodo.bean.view.CalendarAttr
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.bean.State
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnCalendarStateListener
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.helper.CalendarMover
import kotlin.math.abs


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

    private lateinit var mCalendarAdapter: CalendarAdapter

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        orientation = VERTICAL
        View.inflate(getContext(), R.layout.calendar_pager, this)
        viewPager2 = findViewById<ViewPager2>(R.id.calendar_pager).apply {
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

    fun initAdapter(
        onSelectDateListener: OnSelectDateListener,
        onSelectedDateHide: (Boolean) -> Unit,
        calendarWeekView: CalendarWeekView,
        iDayDrawer: IDayDrawer
    ) {
        mCalendarWeekView = calendarWeekView
        mCalendarAdapter = CalendarAdapter(object : OnSelectDateListener {

            override fun onSelectOtherMonth(offset: Int) {
                onSelectDateListener.onSelectOtherMonth(offset)
                viewPager2.currentItem = currentPosition + offset
                mCalendarWeekView.changePosition(currentPosition,true)
            }

            override fun onSelectDate(calendarData: CalendarData, row : Int, col : Int, type : CalendarAttr.CalendarType, state:State) {
                onSelectDateListener.onSelectDate(calendarData,row, col, type, state)
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
        viewPager2.adapter = mCalendarAdapter
        viewPager2.setCurrentItem(CURRENT_DAY_INDEX, false)
    }

    fun isAllowSlide(state: Boolean) {
        viewPager2.isUserInputEnabled = state
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    fun backToday(){
        mCalendarAdapter.backToday()
        viewPager2.currentItem = CURRENT_DAY_INDEX
    }


    //解决主页面内层Vp和外层Vp滑动冲突
    private var mInitialTouchX = 0F
    private var mInitialTouchY = 0F
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mInitialTouchX = ev.x
                mInitialTouchY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.rawX - mInitialTouchX
                val dy = ev.rawY - mInitialTouchY

                val angle = abs(dy) / abs(dx)
                if (angle < 0.8f) {
                    //拦截父类
                    requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_UP -> {
                requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_CANCEL -> {
                requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun moveOffset(offset : Int){
        val view = mCalendarAdapter.getCalendarView(currentPosition) ?: return
        view.moveByOutside(offset)
    }

    fun notifyClickDone(offset: Int) : CalendarMover.CalendarState?{
        val view = mCalendarAdapter.getCalendarView(currentPosition) ?: return null
        return view.notifyClickDone(offset)
    }


}