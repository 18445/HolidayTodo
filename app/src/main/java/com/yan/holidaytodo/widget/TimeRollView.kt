package com.yan.holidaytodo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.util.*
import kotlin.math.abs

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      TimeRollView
 * @Author:         Yan
 * @CreateDate:     2022年07月24日 02:51:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    可滑动的控件
 */
class TimeRollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View (context, attrs, defStyleAttr, defStyleRes){

    // 曲线画笔
    private val curveLinePaint: Paint = Paint()
    // 白圆画笔
    private val circlePaint: Paint = Paint()
    // 时间数字画笔
    private val textPaint: Paint = Paint()
    //圆心X坐标
    private var circleCenterX = 0
    //圆心Y坐标
    private var circleCenterY = 0
    //圆的半径
    private val circleRadius = 30
    //小圆上面的曲线path
    private val linePath: Path = Path()

    private val today = CalendarData(getYear(), getMonth(), getDay())

    private var todayString : String
    private var tomorrowString : String

    private var viewWidth = 0
    //时间text的X坐标
    private var timeTextX1 = 0
    private var timeTextX2 = 0
    private var timeTextX3 = 0
    private var timeTextX4 = 0
    private var timeTextX5 = 0

    init {
        curveLinePaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            strokeWidth = 1.dpToPx().toFloat()
            isAntiAlias = true
        }

        circlePaint.apply {
            style = Paint.Style.FILL
            strokeWidth = 1f
            color = Color.WHITE
        }

        textPaint.apply {
            isAntiAlias = true
            textSize = 10.spToPx().toFloat()
            textAlign = Paint.Align.CENTER
            color = Color.LTGRAY
        }

        val month = today.month
        val day = today.day
        val monthString = if (month < 10){ "0$month"}else{month.toString()}
        val dayString = if (day < 10){ "0$day"}else{day.toString()}
        todayString = "$monthString/$dayString"

        val monthTime = getMonthDays(today.year,today.month)

        val tomorrowMonth =
            if (day + 1 <= monthTime){//在当前月份
                month
            }else{//超出当月日期
                if (month == 12){
                    1
                }else{
                    month + 1
                }
            }

        val tomorrowDay =
            if (day + 1 <= monthTime){
                day + 1
            }else{//超出当月日期
                if (month == 12){
                    1
                }else{
                    1
                }
            }

        val tomorrowMonthString = if (tomorrowMonth < 10){ "0$tomorrowMonth"}else{tomorrowMonth.toString()}
        val tomorrowDayString = if (tomorrowDay < 10){ "0$tomorrowDay"}else{tomorrowDay.toString()}
        tomorrowString = "$tomorrowMonthString/$tomorrowDayString"

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        circleCenterX = viewWidth/2
        circleCenterY = h - circleRadius * 2
        timeTextX1 = circleRadius * 3
        timeTextX2 = viewWidth / 4 + circleRadius * 3 / 2
        timeTextX3 = viewWidth / 2
        timeTextX4 = viewWidth / 2 + viewWidth /4 - circleRadius * 3 / 2
        timeTextX5 = viewWidth - circleRadius * 3

        val colors = intArrayOf(
            Color.parseColor("#000000"),
            Color.parseColor("#152331"),
        )
        val positions = floatArrayOf(0.0f, 1.0f)
        val linearGradient = LinearGradient(viewWidth / 2f,0f, viewWidth / 2f,h.toFloat(), colors, positions, Shader.TileMode.CLAMP)
        curveLinePaint.shader = linearGradient
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(circleCenterX.toFloat(),circleCenterY.toFloat(),circleRadius.toFloat(),circlePaint)
        drawLine(canvas)
        drawText(canvas)
    }

    /**
     * 绘制文字信息
     */
    private fun drawText(canvas: Canvas) {

        drawTextInfo(canvas, todayString, "00:00", timeTextX1)
        drawTextInfo(canvas, null, "15:00", timeTextX2)
        drawTextInfo(canvas, null, "30:00", timeTextX3)
        drawTextInfo(canvas, null, "45:00", timeTextX4)
        drawTextInfo(canvas, tomorrowString, "60:00", timeTextX5)
    }

    private fun drawTextInfo(canvas: Canvas,date : String?,time : String,dis : Int){
        if (abs(circleCenterX - dis) < 50) { //移动正上方情况
            canvas.drawText(time, dis.toFloat(), circleCenterY - circleRadius - 25f, textPaint)
            if (date == null)
                return
            canvas.drawText(date, dis.toFloat(), circleCenterY - circleRadius - 55f, textPaint)
        } else if (abs(circleCenterX - dis) > 110) { //一般情况
            canvas.drawText(time, dis.toFloat(), circleCenterY - 10f, textPaint)
            if (date == null)
                return
            canvas.drawText(date, dis.toFloat(), circleCenterY - 40f, textPaint)
        } else { //滑动情况
            canvas.drawText(time, dis.toFloat(),
                (circleRadius + 15) * (abs(circleCenterX - dis) - 50f) / 60 + (circleCenterY - circleRadius - 25), textPaint)
            if (date == null)
                return
            canvas.drawText(date, dis.toFloat(),
                (circleRadius + 15) * (abs(circleCenterX - dis) - 50f) / 60 + (circleCenterY - circleRadius - 53), textPaint)
        }
    }

    //贝塞尔曲线绘画
    private fun drawLine(canvas: Canvas) {
        linePath.reset()
        linePath.moveTo(0f, circleCenterY.toFloat())
        linePath.lineTo((circleCenterX - circleRadius - 60).toFloat(), circleCenterY.toFloat())
        //在原点绘制三阶贝塞尔曲线
        linePath.cubicTo((circleCenterX - circleRadius).toFloat(), circleCenterY.toFloat(),
            (circleCenterX - circleRadius - 5f), (circleCenterY - circleRadius - 10f),
            circleCenterX.toFloat(), (circleCenterY - circleRadius - 10f))
        linePath.cubicTo((circleCenterX + circleRadius + 5f), (circleCenterY - circleRadius - 10f),
            (circleCenterX + circleRadius).toFloat(), circleCenterY.toFloat(),
            (circleCenterX + circleRadius + 60).toFloat(), circleCenterY.toFloat())
        linePath.lineTo(viewWidth.toFloat(), circleCenterY.toFloat())
        linePath.lineTo(viewWidth.toFloat(), 0f)
        linePath.lineTo(0f, 0f)
        linePath.close()
        canvas.drawPath(linePath, curveLinePaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.x < circleRadius || event.x > viewWidth - circleRadius)
                    return super.onTouchEvent(event)
                circleCenterX = event.x.toInt()
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> if (event.x < viewWidth - circleRadius && event.x > circleRadius) {
                circleCenterX = event.x.toInt()
                invalidate()
            }
        }
        return true
    }

    fun getCurrentCircle() : Int{
        val second =
        if (circleCenterX == width / 2){
            circleCenterX * 60 * 60 / width
        }else if (circleCenterX < width / 4){
            (circleCenterX - 100) * 60 * 60 / width
        }else if (circleCenterX < width / 2){
            (circleCenterX - 50) * 60 * 60 / width
        }else if(circleCenterX < 3 * width / 4){
            (circleCenterX + 50) * 60 * 60 / width
        }else{
            (circleCenterX + 100) * 60 * 60 / width
        }
        return if(second <= 0){
            0
        }else if(second >= 60 * 60){
            60 * 60
        }else{
            second
        }
    }

}