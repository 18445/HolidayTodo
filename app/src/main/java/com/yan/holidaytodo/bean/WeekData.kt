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
class WeekData (private val row : Int){


    //一周中的每一天

//    val days : Array<Day> = Array(CalendarView.TOTAl_COLUMN){
//        Day(State.CURRENT_MONTH,CalendarData(1,2,3),4,5)
//    }
    val days : Array<Day> = arrayOf(
        Day(State.CURRENT_MONTH,CalendarData(2022,7,15),row,0),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,16),row,1),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,17),row,2),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,18),row,3),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,19),row,4),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,20),row,5),
        Day(State.CURRENT_MONTH,CalendarData(2022,7,21),row,6),
    )
}