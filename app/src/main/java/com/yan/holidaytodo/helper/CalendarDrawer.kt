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
import com.yan.holidaytodo.widget.MonthView

/**
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.callback
 * @ClassName:      CalendarRenderer
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 19:30:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    负责绘画日历
 */
class CalendarDrawer(
    private val context: Context,
    private val calendarView: CalendarView,
    private var calendarAttr: CalendarAttr,
) {

    private lateinit var weeks: Array<WeekData>

    //种子日期
    lateinit var seedDate: CalendarData

    //每一天的drawer
    lateinit var dayDrawer: IDayDrawer

    //点击回调
    private lateinit var onSelectDateListener: OnSelectDateListener

    //被选中的日期
    private lateinit var selectedDate: CalendarData

    //被选中的行数
    var selectedRowIndex: Int = 0

    fun initSeedData(position: Int) {
        val offsetMonth = position - MonthView.CURRENT_DAY_INDEX
        val nowData = CalendarData(getYear(), getMonth(), getDay())
        seedDate = nowData.modifyMonth(offsetMonth)
        weeks = getTheWholeMonth(seedDate)
        if (MonthView.currentPosition == MonthView.CURRENT_DAY_INDEX){
            calendarView.setSelectedRowIndex(getRowIndexInMonth(nowData,weeks))
        }
    }

    fun setOnSelectDataListener(listener: OnSelectDateListener) {
        onSelectDateListener = listener
    }


    //绘画每一天
    fun drawDays(
        canvas: Canvas, percent: Float,
        cellHeight: Int,
        cellWidth: Int,
        currentCellHeight: Int,
        drawInfo : Boolean) {
        for (row in 0 until CalendarView.TOTAL_ROW) {
            for (col in 0 until CalendarView.TOTAl_COLUMN) {
                //第row行第col列的元素
                dayDrawer.drawDay(canvas, weeks[row].days[col], percent)
                if(drawInfo){
                    drawDayInfo(canvas,row,col,cellHeight, cellWidth,currentCellHeight)
                }
            }
        }

        update()
    }

    //绘制文字信息
    private fun drawDayInfo(
        canvas: Canvas,
        row:Int,
        col: Int,
        cellHeight: Int,
        cellWidth: Int,
        currentCellHeight: Int){
        canvas.drawRect((col*cellWidth).toFloat(),
            (row*currentCellHeight).toFloat()+cellWidth-cellWidth*0.05f,(col+1f)*cellWidth,
            (row*currentCellHeight+cellHeight).toFloat()+cellWidth*0.05f,Paint().apply {
            color = Color.LTGRAY
        })
        canvas.drawText("", col*cellWidth + 0.3f * cellWidth,
            (row*currentCellHeight + cellHeight*1.3f),
            Paint().apply {
            textSize = 35f
            color = Color.GRAY
        })
    }

    /**
     * 点击某一天时改变这一天的状态
     *
     * @return void
     */
    fun onClickDate(col: Int, row: Int) {
        if (col >= CalendarView.TOTAl_COLUMN || row >= CalendarView.TOTAL_ROW) return
        if (calendarAttr.calendarType == CalendarAttr.CalendarType.MONTH) {
            //为月份表达的时候
            selectedDate = weeks[row].days[col].data
            if (weeks[row].days[col].state === State.CURRENT_MONTH) {
                weeks[row].days[col].state = (State.SELECT)
                CalendarAdapter.selectedData = selectedDate
                onSelectDateListener.onSelectDate(selectedDate,row,col)
                seedDate = selectedDate
            } else if (weeks[row].days[col].state === State.PAST_MONTH) {
                CalendarAdapter.selectedData = (selectedDate)
                calendarView.setSelectedRowIndex(getRowIndexInMonth(selectedDate,getTheWholeMonth(selectedDate)))
                onSelectDateListener.onSelectDate(selectedDate,row,col)
                onSelectDateListener.onSelectOtherMonth(-1)
            } else if (weeks[row].days[col].state === State.NEXT_MONTH) {
                CalendarAdapter.selectedData = (selectedDate)
                calendarView.setSelectedRowIndex(getRowIndexInMonth(selectedDate,getTheWholeMonth(selectedDate)))
                onSelectDateListener.onSelectDate(selectedDate,row,col)
                onSelectDateListener.onSelectOtherMonth(1)
            }
            selectedRowIndex = calendarView.getSelectedRowIndex()

        } else {
            //为周表达的时候
            weeks[selectedRowIndex].days[col].state = (State.SELECT)
            selectedDate = weeks[row].days[col].data
            CalendarAdapter.selectedData = (selectedDate)
            onSelectDateListener.onSelectDate(selectedDate,row,col)
            seedDate = selectedDate
        }

    }

    /**
     * 刷新指定行的周数据
     *
     * @param rowIndex
     * @return void
     */
    fun updateWeek(rowIndex: Int) {
        val currentWeekLastDay: CalendarData = getSaturday(seedDate)

        var day: Int = currentWeekLastDay.day
        for (i in CalendarView.TOTAl_COLUMN - 1 downTo 0) {
            val date = currentWeekLastDay.modifyDay(day)
            if (date == CalendarAdapter.selectedData) {
                weeks[rowIndex].days[i].state = State.SELECT
                weeks[rowIndex].days[i].data = date
            } else {
                weeks[rowIndex].days[i].state = State.CURRENT_MONTH
                weeks[rowIndex].days[i].data = date
            }
            day--
        }
    }

    /**
     * 填充月数据
     *
     * @return void
     */
    private fun fillMonth() {
        val lastMonthDays: Int = getMonthDays(seedDate.year, seedDate.month - 1) // 上个月的天数
        val currentMonthDays: Int = getMonthDays(seedDate.year, seedDate.month) // 当前月的天数
        val firstDayPosition: Int = getFirstDayWeekPosition(
            seedDate.year, +
            seedDate.month
        )
        var day = 0
        for (row in 0 until CalendarView.TOTAL_ROW) {
            day = fillWeekInMonth(lastMonthDays, currentMonthDays, firstDayPosition, day, row)
        }
    }

    /**
     * 填充月中周数据
     *
     * @return
     */
    private fun fillWeekInMonth(
        lastMonthDays: Int,
        currentMonthDays: Int,
        firstDayWeek: Int,
        day: Int,
        row: Int,
    ): Int {
        var mDay = day
        for (col in 0 until CalendarView.TOTAl_COLUMN) {
            //当前天数
            val position: Int = col + row * CalendarView.TOTAl_COLUMN
            if (position >= firstDayWeek && position < firstDayWeek + currentMonthDays) {
                mDay++
                fillCurrentMonthDate(mDay, row, col)
            } else if (position < firstDayWeek) {
                fillLastMonth(lastMonthDays, firstDayWeek, row, col, position)
            } else if (position >= firstDayWeek + currentMonthDays) {
                fillNextMonth(currentMonthDays, firstDayWeek, row, col, position)
            }
        }
        return mDay
    }

    /**
     * 填充月中的数据
     *
     * @return void
     */
    private fun fillCurrentMonthDate(day: Int, row: Int, col: Int) {
        val date: CalendarData = seedDate.modifyDay(day)
        if (date == CalendarAdapter.selectedData) {
            weeks[row].days[col].data = date
            weeks[row].days[col].state = State.SELECT
        } else {
            weeks[row].days[col].data = date
            weeks[row].days[col].state = State.CURRENT_MONTH
        }
        if (date == seedDate) {
            selectedRowIndex = row
        }
    }

    private fun fillNextMonth(
        currentMonthDays: Int,
        firstDayWeek: Int,
        row: Int,
        col: Int,
        position: Int,
    ) {
        val date = CalendarData(
            seedDate.year,
            seedDate.month + 1,
            position - firstDayWeek - currentMonthDays + 1
        )
        weeks[row].days[col].data = date
        weeks[row].days[col].state = State.NEXT_MONTH
    }

    private fun fillLastMonth(
        lastMonthDays: Int,
        firstDayWeek: Int,
        row: Int,
        col: Int,
        position: Int,
    ) {
        val date = CalendarData(
            seedDate.year,
            seedDate.month - 1,
            lastMonthDays - (firstDayWeek - position - 1)
        )
        weeks[row].days[col].data = date
        weeks[row].days[col].state = State.PAST_MONTH
    }

    /**
     * 取消日期的选中状态
     */
    fun cancelSelectState() {
        for (i in 0 until CalendarView.TOTAL_ROW) {
            for (j in 0 until CalendarView.TOTAl_COLUMN) {
                if (weeks[i].days[j].state == State.SELECT) {
                    weeks[i].days[j].state = State.CURRENT_MONTH
                    resetSelectedRowIndex()
                    break
                }
            }
        }
    }

    /**
     * 根据日期显示出出日历的数据
     *
     * @return void
     */
    fun showDate(calendarData: CalendarData) {
        seedDate = calendarData
        update()
    }

    fun update() {
        fillMonth()
        calendarView.invalidate()
    }

    fun resetSelectedRowIndex() {
        selectedRowIndex = 0
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