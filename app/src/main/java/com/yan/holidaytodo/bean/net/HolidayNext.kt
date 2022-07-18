//package com.yan.holidaytodo.bean
//
///**
// *
// * @ProjectName:    HolidayTodo
// * @Package:        com.yan.holidaytodo.bean
// * @ClassName:      HolidayNext
// * @Author:         Yan
// * @CreateDate:     2022年07月14日 14:46:00
// * @UpdateRemark:   更新说明：
// * @Version:        1.0
// * @Description:    获取指定日期的下一个节假日（如果在放假前有调休，也会返回）
// */
//
//data class HolidayNext(
//    val code: Int,
//    val holiday: Holiday,
//    val type: Type,
//    val workday: Workday,
//)
//
//data class Workday(
//    val after: Boolean,
//    val date: String,
//    val holiday: Boolean,
//    val name: String,
//    val rest: Int,
//    val target: String,
//    val wage: Int,
//)
//
//data class HolidayType(
//    val name: String,
//    val type: Int,
//    val week: Int,
//)
//
//data class WorkdayType(
//    val name: String,
//    val type: Int,
//    val week: Int,
//)