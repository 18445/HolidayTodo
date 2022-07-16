package com.yan.holidaytodo.callback

import android.graphics.Canvas
import com.yan.holidaytodo.bean.Day
import com.yan.holidaytodo.bean.WeekData

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.callback
 * @ClassName:      IDayRenderer
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 19:15:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    负责每一天的绘画
 */
interface IDayDrawer {

    fun drawDay(canvas: Canvas, day: Day, percent: Float)

    fun drawWeek(canvas: Canvas, weekData: WeekData)
}