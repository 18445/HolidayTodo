package com.yan.holidaytodo.widget


import android.content.Context
import android.widget.LinearLayout


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      MonthView
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 17:57:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    Month方法
 */
class MonthView(context: Context) : LinearLayout(context) {

    companion object{
        //初始页面
        const val CURRENT_DAY_INDEX = 1000
        //当前界面
        var currentPosition = CURRENT_DAY_INDEX
    }



}