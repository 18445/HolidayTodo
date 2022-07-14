package com.yan.holidaytodo.bean

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean
 * @ClassName:      DayInfo
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 13:43:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    获得指定日期的节假日信息
 */


data class DayInfo(
    val code: Int,
    val holiday: Holiday,
    val type: Type
)

data class Holiday(
    val after: Boolean,
    val holiday: Boolean,
    val name: String,
    val target: String,
    val wage: Int
)

data class Type(
    val name: String?,
    val type: Int?,
    val week: Int?,
    val holiday: HolidayType?,
    val workday: WorkdayType?
)