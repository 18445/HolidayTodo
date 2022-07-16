package com.yan.holidaytodo.callback

import com.yan.holidaytodo.bean.CalendarData

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.callback
 * @ClassName:      OnSelectDateListener
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 19:12:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    选择日期的回调
 */
interface OnSelectDateListener {

    //点击其他日期
    fun onSelectDate(calendarData: CalendarData)

    //点击其他月份
    fun onSelectOtherMonth(offset: Int)
}