package com.yan.holidaytodo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import kotlin.math.abs

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      MyFrameLayout
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 21:45:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    解决主页面内层Vp和外层Vp滑动冲突
 */

class MyFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var mInitialTouchX = 0F
    private var mInitialTouchY = 0F
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mInitialTouchX = ev.x
                mInitialTouchY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = ev.x - mInitialTouchX
                val dy = ev.y - mInitialTouchY

                val angle = abs(dy) / abs(dx)
                if (angle < 0.8f) {
                    //拦截父类
                    requestDisallowInterceptTouchEvent(true)
                }
            }
            MotionEvent.ACTION_UP -> {
                requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_CANCEL -> {
                requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}