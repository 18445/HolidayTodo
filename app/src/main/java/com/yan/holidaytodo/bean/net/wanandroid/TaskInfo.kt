package com.yan.holidaytodo.bean.net.wanandroid

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.net.wanandroid
 * @ClassName:      TaskInfo
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 22:49:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    TODO任务信息
 */


data class TaskInfo(
    val id: Int,
    val completeDate: Any,
    val completeDateStr: String,
    val content: String,
    val date: Long,
    val dateStr: String,
    val priority: Int,
    val status: Int,
    val title: String,
    val type: Int,
    val userId: Int
)
