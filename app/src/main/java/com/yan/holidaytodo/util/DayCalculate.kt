package com.yan.holidaytodo.util

import android.annotation.SuppressLint
import com.yan.holidaytodo.bean.Day
import com.yan.holidaytodo.bean.State
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.bean.view.WeekData
import com.yan.holidaytodo.widget.CalendarView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.util
 * @ClassName:      DayCalculate
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 16:25:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    获得每个月第一天是星期几
 */


/**
 * 得到某一个月的具体天数
 *
 * @param year  参数月所在年
 * @param month 参数月
 * @return int 参数月所包含的天数
 */

fun getMonthDays(year: Int, month: Int): Int {
    var mMonth = month
    var mYear = year
    if (month > 12) {
        mMonth = 1
        mYear += 1
    } else if (month < 1) {
        mMonth = 12
        mYear -= 1
    }
    val monthDays = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    // 闰年2月29天
    if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
        monthDays[1] = 29
    }
    return monthDays[(mMonth - 1) % 12]
}

/**
 * 得到当前月第一天在其周的位置
 *
 * @param year  当前年
 * @param month 当前月
 * @return int 本月第一天在其周的位置
 */
//fun getFirstDayWeekPosition(year: Int, month: Int): Int {
//    val cal = Calendar.getInstance()
//    cal.set(year,month+1,1)
//    return cal[Calendar.DAY_OF_WEEK]
//}
fun getFirstDayWeekPosition(year: Int, month: Int): Int {
    val cal = Calendar.getInstance()
    cal.time = getDateFromString(year, month)
    return cal[Calendar.DAY_OF_WEEK] - 1

}

/**
 * 将yyyy-MM-dd类型的字符串转化为对应的Date对象
 *
 * @param year  当前年
 * @param month 当前月
 * @return Date  对应的Date对象
 */
@SuppressLint("SimpleDateFormat")
fun getDateFromString(year: Int, month: Int): Date {
    val dateString = year.toString() + "-" + (if (month > 9) month else "0$month") + "-01"
    var date = Date()
    try {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        date = sdf.parse(dateString)!!
    } catch (e: ParseException) {
        println(e.message)
    }
    return date
}

/**
 * 计算参数日期月与当前月相差的月份数
 *
 * @param year        参数日期所在年
 * @param month       参数日期所在月
 * @param currentDate 当前月
 * @return int offset 相差月份数
 */
fun calculateMonthOffset(
    year: Int,
    month: Int,
    currentDate: CalendarData,
): Int {
    val currentYear: Int = currentDate.year
    val currentMonth: Int = currentDate.month
    return (year - currentYear) * 12 + (month - currentMonth)
}


/**
 * 得到日期所在周的周六
 *
 * @param seedDate 种子日期
 * @return CalendarDate 所在周周六
 */
@SuppressLint("SimpleDateFormat")
fun getSaturday(seedDate: CalendarData): CalendarData {
    val c = Calendar.getInstance()
    val dateString = "${seedDate.year}-${seedDate.month}-${seedDate.day}"
    var date= Date()
    try {
        val sdf = SimpleDateFormat("yyyy-M-d")
        date = sdf.parse(dateString)!!
    } catch (e: ParseException) {
        println(e.message)
    }
    c.time = date
    c.add(Calendar.DAY_OF_MONTH, 7 - c[Calendar.DAY_OF_WEEK])
    return CalendarData(
        c[Calendar.YEAR],
        c[Calendar.MONTH] + 1,
        c[Calendar.DAY_OF_MONTH]
    )

}

/**
 * 得到一个月个日历表
 */
fun getTheWholeMonth(calendarData: CalendarData) : Array<WeekData> {

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

    return tempWeeks
}


/**
 * 获得所在日历的行数
 */
fun getRowIndexInMonth(data : CalendarData, weeks : Array<WeekData>) : Int{
    for (row in 0 until CalendarView.TOTAL_ROW) {
        for (col in 0 until CalendarView.TOTAl_COLUMN) {
            val day = weeks[row].days[col]
            if(data.year == day.data.year && data.month == day.data.month && data.day == day.data.day){
                return row
            }
        }
    }
    return 0
}

/**
 * 获得当前年份
 */
fun getYear(): Int {
    return Calendar.getInstance()[Calendar.YEAR]
}

/**
 * 获得当前月份
 */
fun getMonth(): Int {
    return Calendar.getInstance()[Calendar.MONTH] + 1
}

/**
 * 获得当前日期
 */
fun getDay(): Int {
    return Calendar.getInstance()[Calendar.DAY_OF_MONTH]
}

/**
 * 判断是否是节假日
 */
fun judgeHoliday(des : String) : Boolean{
    return !(des == "周末" || des == "周一" || des == "周二" || des == "周三" || des == "周四" || des == "周五" || des == "周六")
}

@SuppressLint("SimpleDateFormat")
fun getWeek(calendarData: CalendarData) : String{
    val weeks = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
    val day = "${calendarData.year}-${calendarData.month}-${calendarData.day}"
    val format = SimpleDateFormat("yyyy-MM-dd")
    val date = format.parse(day)
    val calendar = Calendar.getInstance()
    calendar.firstDayOfWeek = Calendar.MONDAY
    calendar.time = date!!
    return weeks[calendar[Calendar.DAY_OF_WEEK] - 1]
}
