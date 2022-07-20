package com.yan.common

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
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
 * @Description:    App
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
//        Handler(mainLooper).post {
//            while (true) {
//                try {
//                    Looper.loop()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        }
    }
}