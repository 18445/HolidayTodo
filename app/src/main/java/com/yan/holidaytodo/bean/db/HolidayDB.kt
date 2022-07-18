package com.yan.holidaytodo.bean.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.db
 * @ClassName:      Holiday
 * @Author:         Yan
 * @CreateDate:     2022年07月18日 23:59:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    数据库数据实体
 */

@Entity(tableName = "HolidayDB")
data class HolidayDB(
    @PrimaryKey val date : String,
    val name : String,
    val year : Int,
    val month : Int,
    val day : Int,
    val holiday : Boolean,

)