package com.yan.holidaytodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.CalendarAttr
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnAdapterSelectListener
import com.yan.holidaytodo.callback.OnCalendarStateListener
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.util.getDay
import com.yan.holidaytodo.util.getMonth
import com.yan.holidaytodo.util.getYear
import com.yan.holidaytodo.widget.CalendarView


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

class CalendarAdapter(
    context: Context,
    private val onSelectDateListener: OnSelectDateListener,
    private val onCalendarStateListener: OnCalendarStateListener,
    private val iDayDrawer: IDayDrawer,
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    //日历view
    private val calendars: HashMap<Int, CalendarView> = HashMap()

    //周布局
    private var calendarType: CalendarAttr.CalendarType = CalendarAttr.CalendarType.MONTH

    //行数
    private var rowCount = CalendarView.TOTAL_ROW

    //今天的日期
    private var seedDate = CalendarData(getYear(), getMonth(), getDay())

    //Adapter回调
    private val onAdapterSelectListener = object : OnAdapterSelectListener {
        override fun cancelSelectState() {
            cancelOtherSelectState()
        }

        override fun updateSelectState() {
            invalidateCurrentCalendar()
        }

    }

    init {
        initCalendar(context, onSelectDateListener)
    }

    companion object {
        var selectedData: CalendarData = CalendarData(getYear(), getMonth(), getDay())
    }

    private fun initCalendar(context: Context, onSelectDateListener: OnSelectDateListener) {
//        for (i in 0..2) {
//            val calendarAttr = CalendarAttr(CalendarAttr.CalendarType.MONTH)
//            val calendarView = CalendarView(context).apply {
//                initOnSelectListener(onSelectDateListener)
//                initAttr(calendarAttr)
//            }
//            calendarView.setOnAdapterSelectListener(object : OnAdapterSelectListener {
//                override fun cancelSelectState() {
//                    cancelOtherSelectState()
//                }
//                override fun updateSelectState() {
//                    invalidateCurrentCalendar()
//                }
//            })
//            calendars.add(calendarView)
//        }
    }

//    fun getPagers(): ArrayList<CalendarView> {
//        return calendars
//    }
//
//    fun getFirstVisibleDate(): CalendarData {
//        return calendars[currentPosition % 3].getFirstDate()
//    }
//
//    fun getLastVisibleDate(): CalendarData {
//        return calendars[currentPosition % 3].getLastDate()
//    }


    fun cancelOtherSelectState() {
        for (CalendarPair in calendars) {
            CalendarPair.value.cancelSelectState()
        }
    }

    fun invalidateCurrentCalendar() {
        for (CalendarPair in calendars) {
            val calendar: CalendarView = CalendarPair.value
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

//    fun switchToMonth() {
//        if (calendars.size > 0 && calendarType !== CalendarAttr.CalendarType.MONTH) {
//            onCalendarTypeChangedListener(CalendarAttr.CalendarType.MONTH)
//            calendarType = CalendarAttr.CalendarType.MONTH
//            MonthView.currentPosition = currentPosition
//            val v: CalendarView = calendars[currentPosition % 3] //0
//            seedDate = v.data
//            val v1: CalendarView = calendars[currentPosition % 3] //0
//            v1.switchCalendarType(CalendarAttr.CalendarType.MONTH)
//            v1.showDate(seedDate)
//            val v2: CalendarView = calendars[(currentPosition - 1) % 3] //2
//            v2.switchCalendarType(CalendarAttr.CalendarType.MONTH)
//            val last: CalendarData = seedDate.modifyMonth(-1)
//            last.day = 1
//            v2.showDate(last)
//            val v3: CalendarView = calendars[(currentPosition + 1) % 3] //1
//            v3.switchCalendarType(CalendarAttr.CalendarType.MONTH)
//            val next: CalendarData = seedDate.modifyMonth(1)
//            next.day = 1
//            v3.showDate(next)
//        }
//    }
//
//    fun switchToWeek(rowIndex: Int) {
//        rowCount = rowIndex
//        if (calendars.size > 0 && calendarType !== CalendarAttr.CalendarType.WEEK) {
//            onCalendarTypeChangedListener(CalendarAttr.CalendarType.WEEK)
//            calendarType = CalendarAttr.CalendarType.WEEK
//            MonthView.currentPosition = currentPosition
//            val v: CalendarView = calendars[currentPosition % 3]
//            seedDate = v.data
//            rowCount = v.getSelectedRowIndex()
//            val v1: CalendarView = calendars[currentPosition % 3]
//            v1.switchCalendarType(CalendarAttr.CalendarType.WEEK)
//            v1.showDate(seedDate)
//            v1.updateWeek(rowIndex)
//            val v2: CalendarView = calendars[(currentPosition - 1) % 3]
//            v2.switchCalendarType(CalendarAttr.CalendarType.WEEK)
//            val last: CalendarData = seedDate.modifyWeek(-1)
//            v2.showDate(getSaturday(last))
//            v2.updateWeek(rowIndex)
//            val v3: CalendarView = calendars[(currentPosition + 1) % 3]
//            v3.switchCalendarType(CalendarAttr.CalendarType.WEEK)
//            val next: CalendarData = seedDate.modifyWeek(1)
//            v3.showDate(getSaturday(next))
//            v3.updateWeek(rowIndex)
//        }
//    }

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
//        if (calendarType === CalendarAttr.CalendarType.WEEK) {
//            MonthView.currentPosition = currentPosition
//            val v1: CalendarView = calendars[currentPosition % 3]
//            v1.showDate(seedDate)
//            v1.updateWeek(rowCount)
//            val v2: CalendarView = calendars[(currentPosition - 1) % 3]
//            val last: CalendarData = seedDate.modifyWeek(-1)
//            v2.showDate(getSaturday(last))
//            v2.updateWeek(rowCount)
//            val v3: CalendarView = calendars[(currentPosition + 1) % 3]
//            val next: CalendarData = seedDate.modifyWeek(1)
//            v3.showDate(getSaturday(next))
//            v3.updateWeek(rowCount)
//        } else {
//            MonthView.currentPosition = currentPosition
//            val v1: CalendarView = calendars[currentPosition % 3] //0
//            v1.showDate(seedDate)
//            val v2: CalendarView = calendars[(currentPosition - 1) % 3] //2
//            val last: CalendarData = seedDate.modifyMonth(-1)
//            last.day = 1
//            v2.showDate(last)
//            val v3: CalendarView = calendars[(currentPosition + 1) % 3] //1
//            val next: CalendarData = seedDate.modifyMonth(1)
//            next.day = 1
//            v3.showDate(next)
//        }
    }

    fun getCalendarType(): CalendarAttr.CalendarType {
        return calendarType
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<CalendarView>(R.id.item_calendar).apply {
            calendars[position] = this
            initAttr(CalendarAttr(CalendarAttr.CalendarType.MONTH))
            initCurrentPosition(position)
            initOnSelectListener(onSelectDateListener)
            initDayDrawer(iDayDrawer)
            initAdapter(onAdapterSelectListener)
            initOnCalendarStateListener(onCalendarStateListener)
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }


}