package com.yan.holidaytodo.bean

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean
 * @ClassName:      CalendarAttr
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 18:57:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    日历的相关属性
 */
class CalendarAttr(var calendarType: CalendarType) {


    //日期格子高度
    var cellHeight = 0

    /**
     * 日期格子宽度
     */
    var cellWidth = 0

    //显示周布局或月布局
    enum class CalendarType {
        WEEK,
        MONTH
    }
}

