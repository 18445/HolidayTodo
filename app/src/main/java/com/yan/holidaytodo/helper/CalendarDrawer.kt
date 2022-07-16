package com.yan.holidaytodo.helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
    private var selectedRowIndex: Int = 0

    fun initSeedData(position: Int) {
        val offsetMonth = position - MonthView.CURRENT_DAY_INDEX
        val nowData = CalendarData(getYear(), getMonth(), getDay())
        seedDate = nowData.modifyMonth(offsetMonth)
        initWeeks(seedDate)
    }

    private fun initWeeks(calendarData: CalendarData) {

        val tempWeeks: Array<WeekData> = Array(CalendarView.TOTAL_ROW) {
            val days = Array(CalendarView.TOTAl_COLUMN) {
                Day(State.CURRENT_MONTH, CalendarData(getYear(), getMonth(), getDay()), 0, 0)
            }
            WeekData(it, days)
        }

        val firstDay = getFirstDayWeekPosition(calendarData.year, calendarData.month)
        val lastCalendarData = calendarData.modifyMonth(-1)
        val nextCalendarData = calendarData.modifyMonth(1)
        val lastDuration = getMonthDays(lastCalendarData.year, lastCalendarData.month)
        val duration = getMonthDays(calendarData.year, calendarData.month)
        val dayList = mutableListOf<Day>()
        //上一月的
        for (i in 0 until firstDay) {
            dayList.add(
                Day(State.PAST_MONTH,
                    CalendarData(lastCalendarData.year,
                        lastCalendarData.month,
                        lastDuration - (firstDay - 1 - i)), 0, 0
                ))
        }
        //这一月的
        for (i in firstDay until firstDay + duration) {
            dayList.add(
                Day(State.CURRENT_MONTH,
                    CalendarData(calendarData.year, calendarData.month, i - firstDay + 1), 0, 0
                ))
        }
        //下一月的
        for (i in firstDay + duration until 42) {
            dayList.add(
                Day(State.NEXT_MONTH,
                    CalendarData(nextCalendarData.year,
                        nextCalendarData.month,
                        i - firstDay - duration + 1), 0, 0)
            )
        }

        for (row in 0 until CalendarView.TOTAL_ROW) {
            for (col in 0 until CalendarView.TOTAl_COLUMN) {
                tempWeeks[row].days[col] = dayList[(col + row * CalendarView.TOTAl_COLUMN)]
                tempWeeks[row].days[col].posRow = row
                tempWeeks[row].days[col].posCol = col
            }
        }

        weeks = tempWeeks

    }

    fun setOnSelectDataListener(listener: OnSelectDateListener) {
        onSelectDateListener = listener
    }

    fun setAttr(attr: CalendarAttr) {
        calendarAttr = attr
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
        dayDrawer.drawWeek(canvas, weeks[5])
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
        canvas.drawText("ABC", col*cellWidth + 0.3f * cellWidth,
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
            if (weeks[row].days[col].state === State.CURRENT_MONTH) {
                weeks[row].days[col].state = (State.SELECT)
                selectedDate = weeks[row].days[col].data
                CalendarAdapter.selectedData = selectedDate
                onSelectDateListener.onSelectDate(selectedDate)
                seedDate = selectedDate
            } else if (weeks[row].days[col].state === State.PAST_MONTH) {
                selectedDate = weeks[row].days[col].data
                CalendarAdapter.selectedData = (selectedDate)
                onSelectDateListener.onSelectOtherMonth(-1)
                onSelectDateListener.onSelectDate(selectedDate)
            } else if (weeks[row].days[col].state === State.NEXT_MONTH) {
                selectedDate = weeks[row].days[col].data
                CalendarAdapter.selectedData = (selectedDate)
                onSelectDateListener.onSelectOtherMonth(1)
                onSelectDateListener.onSelectDate(selectedDate)
            }
        } else {
            //为周表达的时候
            weeks[row].days[col].state = (State.SELECT)
            selectedDate = weeks[row].days[col].data
            CalendarAdapter.selectedData = (selectedDate)
            onSelectDateListener.onSelectDate(selectedDate)
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