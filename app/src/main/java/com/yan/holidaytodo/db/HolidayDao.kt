package com.yan.holidaytodo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yan.holidaytodo.bean.db.HolidayDB

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.db
 * @ClassName:      HolidayDao
 * @Author:         Yan
 * @CreateDate:     2022年07月18日 23:58:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    HolidayDao
 */

@Dao
interface HolidayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg holidayDB : HolidayDB)

    @Query("SELECT * FROM HolidayDB")
    fun getAllHolidays() : Array<HolidayDB>

}