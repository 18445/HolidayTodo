package com.yan.holidaytodo.bean.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.db
 * @ClassName:      TaskDB
 * @Author:         Yan
 * @CreateDate:     2022年07月26日 01:37:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:
 */

@Entity(tableName = "TaskDB")
data class TaskDB (
    @PrimaryKey val id: Int,
    val completeDate: String,
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