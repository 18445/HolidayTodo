package com.yan.holidaytodo.widget

import android.R.attr.text
import android.R.attr.textSize
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.yan.holidaytodo.bean.Day
import com.yan.holidaytodo.callback.IDayDrawer


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      SingleDay
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 15:43:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    日历中每一天天数的单独的View
 */
class SingleDay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context,attrs, defStyleAttr, defStyleRes),IDayDrawer{

    //是否被选中
    private var beChosen = true
    //是否当月key日期
    private var isKey = true
    //日期字体的画笔
    private val mDayPaint : Paint = Paint()
    //日期文字描述画笔
    private val mTextPaint : Paint = Paint()
    //当日日期
    private val mDay : Int = 14
    //当日描述
    private val mText : String = "十六"
    //日期大小
    private val daySize = 70f
    //字体大小
    private val textSize = 40f
    //FrontMetrics对象
    private val fm = mTextPaint.fontMetricsInt

    // getWidth(): 控件的宽度(view的宽度)
    // getHeight()：控件的高度(view的高度)
    // mPaint.measureText(mText)是精确的测出绘制文本的宽度
    // fontMetrics.bottom-fontMetrics.top就是绘制文本的高度。
    // y计算出来就是baseline的纵坐标
    private val middleX
        get() = width / 2f - mDayPaint.measureText(mText) / 2f

    private val middleY
        get() = height / 2f + (fm.bottom - fm.top) / 2f - fm.bottom

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDay(canvas)
        drawText(canvas)
    }

    //绘制日期
    private fun drawDay(canvas: Canvas) {
        mDayPaint.textSize = daySize
        canvas.drawText(mDay.toString(),middleX,middleY,mDayPaint)
    }

    //绘制日期相关文字
    private fun drawText(canvas: Canvas){
        mTextPaint.textSize = textSize
        canvas.drawText(mText,middleX,middleY+height/4,mTextPaint)
    }

    override fun drawDay(canvas: Canvas, day: Day) {
        drawText(canvas)
        drawDay(canvas)
    }


}