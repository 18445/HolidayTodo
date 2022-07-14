package com.yan.holidaytodo.bean

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean
 * @ClassName:      DayBean
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 21:12:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    一天的信息
 */
class Day (var state: State, var data: CalendarData, val posRow : Int, val posCol : Int){
}

enum class State {
    CURRENT_MONTH, PAST_MONTH, NEXT_MONTH, SELECT
}