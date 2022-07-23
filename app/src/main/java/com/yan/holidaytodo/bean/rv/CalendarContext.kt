package com.yan.holidaytodo.bean.rv

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.rv
 * @ClassName:      ItemContent
 * @Author:         Yan
 * @CreateDate:     2022年07月20日 15:03:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    RecycleView的Item 内容项
 */
data class CalendarContext (
    val name : String,
    val date : String,
    val future : Boolean
)