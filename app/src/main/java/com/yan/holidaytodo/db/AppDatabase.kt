package com.yan.holidaytodo.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yan.common.App
import com.yan.holidaytodo.bean.db.HolidayDB

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.db
 * @ClassName:      AppDatabase
 * @Author:         Yan
 * @CreateDate:     2022年07月18日 23:56:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    全局数据库
 */

@Database(entities = [HolidayDB::class],version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun holidayDao() : HolidayDao

    companion object{
        val INSTANCE by lazy {
            Room.databaseBuilder(App.appContext,AppDatabase::class.java,"")
                .build()
        }
    }

}