package com.yan.holidaytodo.bean

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import com.yan.holidaytodo.adapter.CalendarAdapter
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.util.getFirstDayWeekPosition
import com.yan.holidaytodo.util.getMonthDays
import com.yan.holidaytodo.util.getSaturday
import com.yan.holidaytodo.widget.CalendarView

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
class CalendarDrawer(private val context: Context,private val calendarView: CalendarView,private var calendarAttr: CalendarAttr) {

//    private val weeks: Array<WeekData> = Array(CalendarView.TOTAL_ROW){
//        WeekData(it)
//    }

    private val days1 = arrayOf(
        Day(State.PAST_MONTH,CalendarData(2022,6,26),0,0),
        Day(State.PAST_MONTH,CalendarData(2022,6,27),0,1),
        Day(State.PAST_MONTH,CalendarData(2022,6,28),0,2),
        Day(State.PAST_MONTH,CalendarData(2022,6,29),0,3),
        Day(State.PAST_MONTH,CalendarData(2022,6,30),0,4),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,1),0,5),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,2),0,6),
    )

    private val days2 = arrayOf(
        Day(State.CURRENT_MONTH,CalendarData(2022,7,3),1,0),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,4),1,1),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,5),1,2),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,6),1,3),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,7),1,4),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,8),1,5),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,9),1,6),
    )
    private val days3 = arrayOf(
        Day(State.CURRENT_MONTH,CalendarData(2022,7,10),2,0),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,11),2,1),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,12),2,2),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,13),2,3),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,14),2,4),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,15),2,5),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,16),2,6),
    )
    private val days4 = arrayOf(
        Day(State.CURRENT_MONTH,CalendarData(2022,7,17),3,0),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,18),3,1),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,19),3,2),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,20),3,3),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,21),3,4),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,22),3,5),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,23),3,6),
    )
    private val days5 = arrayOf(
        Day(State.CURRENT_MONTH,CalendarData(2022,7,24),4,0),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,25),4,1),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,26),4,2),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,27),4,3),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,28),4,4),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,29),4,5),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,30),4,6),
    )
    private val days6 = arrayOf(
        Day(State.CURRENT_MONTH,CalendarData(2022,7,30),5,0),
        Day(State.NEXT_MONTH,CalendarData(2022,8,1),5,1),
        Day(State.NEXT_MONTH,CalendarData(2022,8,2),5,2),
        Day(State.NEXT_MONTH,CalendarData(2022,8,3),5,3),
        Day(State.NEXT_MONTH,CalendarData(2022,8,4),5,4),
        Day(State.NEXT_MONTH,CalendarData(2022,8,5),5,5),
        Day(State.NEXT_MONTH,CalendarData(2022,8,6),5,6),
    )

    private val weeks : Array<WeekData> = arrayOf(
        WeekData(0, days1),
        WeekData(1, days2),
        WeekData(2, days3),
        WeekData(3, days4),
        WeekData(4, days5),
        WeekData(5, days6),
    )

    lateinit var dayDrawer: IDayDrawer //每一天的drawer

    private lateinit var onSelectDateListener: OnSelectDateListener //点击回调
    //种子日期
     lateinit var seedDate: CalendarData
    //被选中的日期
    private lateinit var selectedDate : CalendarData
    //被选中的行数
    private var selectedRowIndex: Int = 0

    //绘画每一天
    fun drawDays(canvas: Canvas){
        for (row in 0 until CalendarView.TOTAL_ROW) {
            for (col in 0 until CalendarView.TOTAl_COLUMN) {
                //第row行第col列的元素
                Log.d("yanDrawer","$row , $col   "+weeks[row].days[col].data)
                dayDrawer.drawDay(canvas, weeks[row].days[col])
            }
        }
    }

    fun setOnSelectDataListener(listener: OnSelectDateListener){
        onSelectDateListener = listener
    }

    fun setAttr(attr : CalendarAttr){
        calendarAttr = attr
    }

    /**
     * 点击某一天时改变这一天的状态
     *
     * @return void
     */
    fun onClickDate(col: Int, row: Int) {
        if (col >= CalendarView.TOTAl_COLUMN || row >= CalendarView.TOTAL_ROW) return
            if(calendarAttr.calendarType ==  CalendarAttr.CalendarType.MONTH){
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
            }else{
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
            val date  = currentWeekLastDay.modifyDay(day)
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
            seedDate.year,+
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
     * @return void
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
     * 填充当前月份的数据
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

    fun resetSelectedRowIndex(){
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