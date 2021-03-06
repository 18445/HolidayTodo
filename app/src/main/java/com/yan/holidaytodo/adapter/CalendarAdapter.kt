package com.yan.holidaytodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.view.CalendarAttr
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnAdapterSelectListener
import com.yan.holidaytodo.callback.OnCalendarStateListener
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.util.*
import com.yan.holidaytodo.widget.CalendarView
import com.yan.holidaytodo.widget.CalendarWeekView
import com.yan.holidaytodo.widget.MonthView


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
    private val onSelectDateListener: OnSelectDateListener,
    private val onCalendarStateListener: OnCalendarStateListener,
    private val onSelectedDateHide: (Boolean) -> Unit,
    private val calendarWeekView: CalendarWeekView,
    private val iDayDrawer: IDayDrawer,
) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    //日历view
    private val calendars: HashMap<Int, CalendarView> = HashMap()

    //行数
    private val currentRow
        get() = CalendarView.selectedRowIndex

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

    companion object {
        var selectedData: CalendarData = CalendarData(getYear(), getMonth(), getDay())
    }

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
                calendar.updateWeek(currentRow)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
            initOnSelectedDateHide {
                onSelectedDateHide(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    fun backToday(){
        calendars[MonthView.CURRENT_DAY_INDEX]?.performClickToday()
    }

    fun getCalendarView(currentPosition : Int) : CalendarView?{
        return calendars[currentPosition]
    }



}