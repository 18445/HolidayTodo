package com.yan.common

import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common
 * @ClassName:      App
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 15:02:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
class App : Application() {
    companion object {
        lateinit var appContext: Context
            private set
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}