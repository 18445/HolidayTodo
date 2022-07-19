package com.yan.holidaytodo.bean.net.wanandroid

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.net.wanandroid
 * @ClassName:      TaskList
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 22:54:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    任务列表
 */

data class TaskList(
    val curPage: Int,
    val datas: List<TaskInfo>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)
