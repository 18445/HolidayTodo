package com.yan.holidaytodo.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.yan.holidaytodo.callback.IDayDrawer
import kotlin.math.abs

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      InterpretView
 * @Author:         Yan
 * @CreateDate:     2022年07月17日 18:56:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    判断是否拦截事件的View
 */
class InterpretView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {


    private var posX = 0f
    private var posY = 0f

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        Log.e("totalOffset","onTouched1111")
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                posX = event.x
                posY = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                val totalOffsetY = event.y - posY

                if(abs(totalOffsetY) > 20 ){
                    return true
                }

            }

        }
        return super.onInterceptTouchEvent(event)
    }


}