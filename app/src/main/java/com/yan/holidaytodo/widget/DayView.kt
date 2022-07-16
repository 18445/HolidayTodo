package com.yan.holidaytodo.widget

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.Day
import com.yan.holidaytodo.bean.WeekData
import com.yan.holidaytodo.callback.IDayDrawer
import kotlin.time.Duration.Companion.days

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      DayView
 * @Author:         Yan
 * @CreateDate:     2022年07月15日 03:10:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:
 */
abstract class DayView(context: Context, layoutResource: Int) :
    RelativeLayout(context), IDayDrawer {
    lateinit var day: Day

    /**
     * 为自定义的DayView设置资源文件
     *
     * @param layoutResource 资源文件
     * @return CalendarDate 修改后的日期
     */
    private fun setupLayoutResource(layoutResource: Int) {
        val view = LayoutInflater.from(context).inflate(layoutResource, this)
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        view.layout(0, 0, measuredWidth, measuredHeight)
    }

    init {
        setupLayoutResource(layoutResource)
    }

    open fun refreshContent() {
        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        layout(0, 0, measuredWidth, measuredHeight)
    }

    override fun drawDay(canvas: Canvas, day: Day ,percent : Float) {
        this.day = day
        refreshContent()
        val saveId = canvas.save()
        canvas.translate(getTranslateX(canvas, day).toFloat(),
            (day.posRow * measuredHeight + 150 * day.posRow * percent))
        draw(canvas)
        canvas.restoreToCount(saveId)
    }

//    fun drawWeekDay(canvas: Canvas,day: Day){
//
//    }
//
    override fun drawWeek(canvas: Canvas,weekData: WeekData){
//        for(i in 0 until CalendarView.TOTAl_COLUMN){
//            drawDay(canvas,weekData)
//        }
    }

    private fun getTranslateX(canvas: Canvas, day: Day): Int {
        val canvasWidth = canvas.width / 7
        val viewWidth = measuredWidth
        val moveX = (canvasWidth - viewWidth) / 2
        return day.posCol * canvasWidth + moveX
    }




}