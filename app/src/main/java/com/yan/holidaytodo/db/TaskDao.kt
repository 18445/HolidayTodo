package com.yan.holidaytodo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yan.holidaytodo.bean.db.TaskDB

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.db
 * @ClassName:      TaskDao
 * @Author:         Yan
 * @CreateDate:     2022年07月21日 17:40:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    任务Dao
 */

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg taskDB : TaskDB)

    @Query("SELECT * FROM TaskDB")
    fun getAllTasks() : List<TaskDB>?

    @Query("SELECT * FROM TaskDB WHERE id = :id")
    fun getTaskById(id : String) : TaskDB?

    @Query("DELETE FROM TaskDB WHERE id = :id")
    fun deleteTaskById(id : String)

}