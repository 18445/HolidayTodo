package com.yan.holidaytodo.bean

import com.yan.holidaytodo.widget.CalendarView

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean
 * @ClassName:      WeekData
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 19:46:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    周数据
 */
class WeekData {
    private val row : Int = CalendarView.TOTAL_ROW
    val days : Array<Day> = Array(row){
        Day(State.CURRENT_MONTH,CalendarData(1,2,3),4,5)
    }
}