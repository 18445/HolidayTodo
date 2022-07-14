package com.yan.holidaytodo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      WeekView
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 18:28:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    顶部View
 */
class WeekView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context,attrs, defStyleAttr, defStyleRes){

    private val DEFAULT_DAYS_IN_WEEK = 7

    private val calendar = Calendar.getInstance()

    private val mTextSize = 20f

    private fun addView() {
        for (i in 1..DEFAULT_DAYS_IN_WEEK) {
            val weekTextView = TextView(context)
            calendar[Calendar.DAY_OF_WEEK] = i
            weekTextView.textSize = mTextSize
            weekTextView.gravity = Gravity.CENTER
            weekTextView.text =
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
            addView(weekTextView)
            calendar.add(Calendar.DATE, 1)
        }
    }

    init {
        addView()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        var specHeightSize = MeasureSpec.getSize(heightMeasureSpec)
        val specHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        //wrap
        //成正方形
        if (specHeightMode == MeasureSpec.AT_MOST) {
            specHeightSize =
                specWidthSize / DEFAULT_DAYS_IN_WEEK
        }

        setMeasuredDimension(specWidthSize, specHeightSize)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                specWidthSize / DEFAULT_DAYS_IN_WEEK,
                MeasureSpec.EXACTLY
            )
            val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                specHeightSize,
                MeasureSpec.EXACTLY
            )
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val width = child.measuredWidth
            val height = child.measuredHeight
            child.layout(childLeft, 0, childLeft + width, height)
            childLeft += width
        }
    }

}