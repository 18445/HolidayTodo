package com.yan.holidaytodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.CalendarAttr
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnAdapterSelectListener
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.util.getDay
import com.yan.holidaytodo.util.getMonth
import com.yan.holidaytodo.util.getSaturday
import com.yan.holidaytodo.util.getYear
import com.yan.holidaytodo.widget.CalendarView
import com.yan.holidaytodo.widget.MonthView
import java.util.*


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.adapter
 * @ClassName:      CalendarBaseAdapter
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 18:00:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    日历的adapter
 */

class CalendarAdapter(private val context: Context,
                      private val onSelectDateListener: OnSelectDateListener,
                      private val iDayDrawer: IDayDrawer) :  RecyclerView.Adapter<CalendarAdapter.ViewHolder>(){

    //日历view
    private val calendars: ArrayList<CalendarView> = ArrayList()
    //当前页数
    private var currentPosition: Int = MonthView.CURRENT_DAY_INDEX
    //周布局
    private var calendarType: CalendarAttr.CalendarType = CalendarAttr.CalendarType.MONTH
    //行数
    private var rowCount = CalendarView.TOTAL_ROW
    //今天的日期
    private var seedDate = CalendarData(getYear(), getMonth(), getDay())
    //状态改变回调监听
    private var onCalendarTypeChangedListener : (CalendarAttr.CalendarType) -> Unit = {}


    init{
        initCalendar(context, onSelectDateListener)
    }

    companion object{
        var selectedData: CalendarData = CalendarData(getYear(), getMonth(), getDay())
    }

    private fun initCalendar(context: Context, onSelectDateListener: OnSelectDateListener) {
        for (i in 0..2) {
            val calendarAttr = CalendarAttr(CalendarAttr.CalendarType.MONTH)
            calendarAttr.calendarType = (CalendarAttr.CalendarType.WEEK)
            val calendarView = CalendarView(context).apply {
                initOnSelectListener(onSelectDateListener)
                initAttr(calendarAttr)
            }
            calendarView.setOnAdapterSelectListener(object : OnAdapterSelectListener {
                override fun cancelSelectState() {
                    cancelOtherSelectState()
                }
                override fun updateSelectState() {
                    invalidateCurrentCalendar()
                }
            })
            calendars.add(calendarView)
        }
    }

    fun getPagers(): ArrayList<CalendarView> {
        return calendars
    }

    fun getFirstVisibleDate(): CalendarData {
        return calendars[currentPosition % 3].getFirstDate()
    }

    fun getLastVisibleDate(): CalendarData {
        return calendars[currentPosition % 3].getLastDate()
    }

    fun cancelOtherSelectState() {
        for (i in calendars.indices) {
            val calendarView : CalendarView = calendars[i]
            calendarView.cancelSelectState()
        }
    }

    fun invalidateCurrentCalendar() {
        for (i in calendars.indices) {
            val calendar: CalendarView = calendars[i]
            calendar.update()
            if (calendar.type == CalendarAttr.CalendarType.WEEK) {
                calendar.updateWeek(rowCount)
            }
        }
    }

    private fun setMarkData(markData: HashMap<String, String>) {
        setMarkData(markData)
        notifyDataChanged()
    }

    fun switchToMonth() {
        if (calendars.size > 0 && calendarType !== CalendarAttr.CalendarType.MONTH) {
            onCalendarTypeChangedListener(CalendarAttr.CalendarType.MONTH)
            calendarType = CalendarAttr.CalendarType.MONTH
            MonthView.currentPosition = currentPosition
            val v: CalendarView = calendars[currentPosition % 3] //0
            seedDate = v.data
            val v1: CalendarView = calendars[currentPosition % 3] //0
            v1.switchCalendarType(CalendarAttr.CalendarType.MONTH)
            v1.showDate(seedDate)
            val v2: CalendarView = calendars[(currentPosition - 1) % 3] //2
            v2.switchCalendarType(CalendarAttr.CalendarType.MONTH)
            val last: CalendarData = seedDate.modifyMonth(-1)
            last.day = 1
            v2.showDate(last)
            val v3: CalendarView = calendars[(currentPosition + 1) % 3] //1
            v3.switchCalendarType(CalendarAttr.CalendarType.MONTH)
            val next: CalendarData = seedDate.modifyMonth(1)
            next.day = 1
            v3.showDate(next)
        }
    }

    fun switchToWeek(rowIndex: Int) {
        rowCount = rowIndex
        if (calendars.size > 0 && calendarType !== CalendarAttr.CalendarType.WEEK) {
            onCalendarTypeChangedListener(CalendarAttr.CalendarType.WEEK)
            calendarType = CalendarAttr.CalendarType.WEEK
            MonthView.currentPosition = currentPosition
            val v: CalendarView = calendars[currentPosition % 3]
            seedDate = v.data
            rowCount = v.getSelectedRowIndex()
            val v1: CalendarView = calendars[currentPosition % 3]
            v1.switchCalendarType(CalendarAttr.CalendarType.WEEK)
            v1.showDate(seedDate)
            v1.updateWeek(rowIndex)
            val v2: CalendarView = calendars[(currentPosition - 1) % 3]
            v2.switchCalendarType(CalendarAttr.CalendarType.WEEK)
            val last: CalendarData = seedDate.modifyWeek(-1)
            v2.showDate(getSaturday(last))
            v2.updateWeek(rowIndex)
            val v3: CalendarView = calendars[(currentPosition + 1) % 3]
            v3.switchCalendarType(CalendarAttr.CalendarType.WEEK)
            val next: CalendarData = seedDate.modifyWeek(1)
            v3.showDate(getSaturday(next))
            v3.updateWeek(rowIndex)
        }
    }

    fun notifyMonthDataChanged(date: CalendarData) {
        seedDate = date
        refreshCalendar()
    }

    fun notifyDataChanged(date: CalendarData) {
        seedDate = date
        selectedData = date
        refreshCalendar()
    }

    private fun notifyDataChanged() {
        refreshCalendar()
    }

    private fun refreshCalendar() {
        if (calendarType === CalendarAttr.CalendarType.WEEK) {
            MonthView.currentPosition = currentPosition
            val v1: CalendarView = calendars[currentPosition % 3]
            v1.showDate(seedDate)
            v1.updateWeek(rowCount)
            val v2: CalendarView = calendars[(currentPosition - 1) % 3]
            val last: CalendarData = seedDate.modifyWeek(-1)
            v2.showDate(getSaturday(last))
            v2.updateWeek(rowCount)
            val v3: CalendarView = calendars[(currentPosition + 1) % 3]
            val next: CalendarData = seedDate.modifyWeek(1)
            v3.showDate(getSaturday(next))
            v3.updateWeek(rowCount)
        } else {
            MonthView.currentPosition = currentPosition
            val v1: CalendarView = calendars[currentPosition % 3] //0
            v1.showDate(seedDate)
            val v2: CalendarView = calendars[(currentPosition - 1) % 3] //2
            val last: CalendarData = seedDate.modifyMonth(-1)
            last.day = 1
            v2.showDate(last)
            val v3: CalendarView = calendars[(currentPosition + 1) % 3] //1
            val next: CalendarData = seedDate.modifyMonth(1)
            next.day = 1
            v3.showDate(next)
        }
    }

    fun getCalendarType(): CalendarAttr.CalendarType {
        return calendarType
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.findViewById<CalendarView>(R.id.item_calendar).apply {
                    initAttr(CalendarAttr(CalendarAttr.CalendarType.MONTH))
                    initOnSelectListener(onSelectDateListener)
                    initDayDrawer(iDayDrawer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar,parent,false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }


}