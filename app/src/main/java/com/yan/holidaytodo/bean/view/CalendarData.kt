package com.yan.holidaytodo.bean.view

import android.util.Log
import com.yan.holidaytodo.util.getDay
import com.yan.holidaytodo.util.getMonth
import com.yan.holidaytodo.util.getMonthDays
import com.yan.holidaytodo.util.getYear
import java.util.*
import kotlin.math.abs

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean
 * @ClassName:      DayBan
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 16:48:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    在日期中的每一个Day类
 */
data class CalendarData(
    var year: Int,
    var month: Int,
    var day: Int,
) {

    init {
        //初始化日期
        if (month > 12) {
            month = 1
            year++
        } else if (month < 1) {
            month = 12
            year--
        }
    }


    /**
     * 通过修改当前Date对象的天数返回一个修改后的Date
     *
     * @param day 修改的日期
     * @return CalendarData 修改后的日期
     */
    fun modifyDay(day: Int): CalendarData {
        val lastMonthDays: Int = getMonthDays(year, month - 1)
        val currentMonthDays: Int = getMonthDays(year, month)
        val modifyDate: CalendarData =
            if (day > currentMonthDays) {
                CalendarData(year, month, this.day)
            } else if (day > 0) {
                CalendarData(year, month, day)
            } else if (day > 0 - lastMonthDays) {
                CalendarData(year, month - 1, lastMonthDays + day)
            } else {
                CalendarData(year, month, this.day)
            }
        return modifyDate
    }

    /**
     * 通过修改当前Date对象的所在周返回一个修改后的Date
     *
     * @param offset 修改的周数
     * @return CalendarData 修改后的日期
     */
    fun modifyWeek(offset: Int): CalendarData {
        val result = CalendarData(getDay(), getMonth(), getYear())
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month - 1
        c[Calendar.DAY_OF_MONTH] = day
        c.add(Calendar.DATE, offset * 7)
        result.year = (c[Calendar.YEAR])
        result.month = (c[Calendar.MONTH] + 1)
        result.day = (c[Calendar.DATE])
        return result
    }

    /**
     * 通过修改当前Date对象的所在月返回一个修改后的Date
     *
     * @param offset 修改的日期
     * @return CalendarData 修改后的日期
     */
    fun modifyMonth(offset: Int): CalendarData {
        val mMonth: Int
        val mYear: Int
        val addToMonth = month + offset
        if (offset > 0) {
            if (addToMonth > 12) {
                mYear = (year + (addToMonth - 1) / 12)
                mMonth = (if (addToMonth % 12 == 0) 12 else addToMonth % 12)
            } else {
                mYear = (year)
                mMonth = addToMonth
            }
        } else {
            if (addToMonth == 0) {
                mYear = year - 1
                mMonth = 12
            } else if (addToMonth < 0) {
                mYear = year + addToMonth / 12 - 1
                val month = 12 - abs(addToMonth) % 12
                mMonth = (if (month == 0) 12 else month)
            } else {
                mYear = year
                mMonth = if (addToMonth == 0) 12 else addToMonth
            }
        }

        return CalendarData(
            mYear.getNoZeroInt(getYear()),
            mMonth.getNoZeroInt(getMonth()),
            getDay())
    }

    private fun Int.getNoZeroInt(backup: Int): Int {
        return if (this == 0) backup else this
    }

}