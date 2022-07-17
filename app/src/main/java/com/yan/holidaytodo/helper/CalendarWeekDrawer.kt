package com.yan.holidaytodo.helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.yan.holidaytodo.adapter.CalendarAdapter
import com.yan.holidaytodo.bean.*
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.util.*
import com.yan.holidaytodo.widget.CalendarView
import com.yan.holidaytodo.widget.CalendarWeekView
import com.yan.holidaytodo.widget.MonthView

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.helper
 * @ClassName:      CalendarWeekDrawer
 * @Author:         Yan
 * @CreateDate:     2022年07月17日 17:26:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    日历周的绘画
 */
class CalendarWeekDrawer (
    private val context: Context,
    private val calendarWeekView: CalendarWeekView,
) {
    //一个月总日期
    private lateinit var weeks: Array<WeekData>
    //种子日期
    lateinit var seedDate: CalendarData
    //完整当前日期
    private fun wholeWeeks(position: Int) : Array<WeekData> = getTheWholeMonth(CalendarData(getYear(), getMonth(), getDay()).modifyMonth(position))

    //每一天的drawer
    lateinit var dayDrawer: IDayDrawer

    //点击回调
    private lateinit var onSelectDateListener: OnSelectDateListener

    //被选中的日期
    private lateinit var selectedDate: CalendarData

    //被选中的行数
    var selectedRowIndex: Int = 0

    //当前所在天数
    private val position : Int
        get() = MonthView.currentPosition

    fun initSeedData(position: Int) {
        val offsetMonth = position - MonthView.CURRENT_DAY_INDEX
        val nowData = CalendarData(getYear(), getMonth(), getDay())
        seedDate = nowData.modifyMonth(offsetMonth)
        weeks = getTheWholeMonth(seedDate)
    }

    fun changePosition(position : Int){
        weeks = wholeWeeks(position - MonthView.CURRENT_DAY_INDEX)
    }

    fun changeSelectedData(calendarData: CalendarData) : Int{
        selectedRowIndex = getRowIndexInMonth(calendarData,wholeWeeks(MonthView.currentPosition - MonthView.CURRENT_DAY_INDEX))
        var col = -1
        for(i in 0 until CalendarView.TOTAl_COLUMN){
            val day = weeks[selectedRowIndex].days[i]
            if (calendarData.day == day.data.day && calendarData.month == day.data.month){
                col = i
            }
        }
        onClickDate(calendarData,selectedRowIndex,col)

        return selectedRowIndex
    }
    fun setOnSelectDataListener(listener: OnSelectDateListener) {
        onSelectDateListener = listener
    }

    //绘画每一天
    fun drawDaysOfWeek(canvas: Canvas) {
        //写被画出的位置
        Log.e("currentRow2",selectedRowIndex.toString())
        dayDrawer.drawWeek(canvas,weeks[selectedRowIndex])
        updateWeek(selectedRowIndex)
    }

    /**
     * 点击某一天时改变这一天的状态
     *
     * @return void
     */
    fun onClickDate(calendarData: CalendarData,row : Int,col: Int) {
        if (col >= CalendarView.TOTAl_COLUMN || selectedRowIndex >= CalendarView.TOTAL_ROW) return

            seedDate = calendarData
            cancelSelectState()
            selectedRowIndex = row
            selectedDate = weeks[selectedRowIndex].days[col].data
            weeks[row].days[col].state = (State.SELECT)
            updateWeek(selectedRowIndex)

    }

    /**
     * 刷新指定行的周数据
     *
     * @param rowIndex
     * @return void
     */
    fun updateWeek(rowIndex: Int) {
        Log.e("currentRow1",rowIndex.toString())
        val currentWeekLastDay: CalendarData = getSaturday(seedDate)

        var day: Int = currentWeekLastDay.day
        for (i in CalendarView.TOTAl_COLUMN - 1 downTo 0) {
            val date = currentWeekLastDay.modifyDay(day)
            if (date == CalendarAdapter.selectedData) {
                weeks[rowIndex].days[i].state = State.SELECT
                weeks[rowIndex].days[i].data = date
            } else {
                weeks[rowIndex].days[i].state = wholeWeeks(position - MonthView.CURRENT_DAY_INDEX)[rowIndex].days[i].state
                weeks[rowIndex].days[i].data = date
            }
            day--
        }
    }

    /**
     * 取消日期的选中状态
     */
    fun cancelSelectState() {

        weeks = wholeWeeks(position - MonthView.CURRENT_DAY_INDEX)

    }

    fun getFirstDate(): CalendarData {
        val week: WeekData = weeks[0]
        val day: Day = week.days[0]
        return day.data
    }

    fun getLastDate(): CalendarData {
        val week: WeekData = weeks[weeks.size - 1]
        val day: Day = week.days[week.days.size - 1]
        return day.data
    }

}