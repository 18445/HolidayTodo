package com.yan.holidaytodo.callback

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.interface
 * @ClassName:      ICalendarView
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 17:54:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:
 */
interface ICalendarView {
    fun backToday()

    fun currentIdx()

    fun focusCalendar()

    fun reDraw()
}