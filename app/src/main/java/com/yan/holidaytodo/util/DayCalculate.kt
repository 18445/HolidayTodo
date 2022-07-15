package com.yan.holidaytodo.util

import android.annotation.SuppressLint
import com.yan.holidaytodo.bean.CalendarData
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
    return monthDays[(mMonth - 1)%12]
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
    val dateString: String = seedDate.toString()
    var date: Date? = null
    try {
        val sdf = SimpleDateFormat("yyyy-M-d")
        date = sdf.parse(dateString)
    } catch (e: ParseException) {
        println(e.message)
    }
    c.time = date!!
    c.add(Calendar.DAY_OF_MONTH, 7 - c[Calendar.DAY_OF_WEEK])
    return CalendarData(
        c[Calendar.YEAR],
        c[Calendar.MONTH] + 1,
        c[Calendar.DAY_OF_MONTH]
    )
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
