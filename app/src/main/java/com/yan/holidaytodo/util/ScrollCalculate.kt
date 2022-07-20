package com.yan.holidaytodo.util

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.icu.text.CaseMap
import android.os.Message
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Scroller
import android.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.bean.view.GestureBean
import com.yan.holidaytodo.bean.view.ViewHandler

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.util
 * @ClassName:      scrollCalculate
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 20:30:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    计算移动的类
 */


/**
 * 计算偏移距离
 *
 * @param offset 偏移值
 * @param min    最小偏移值
 * @param max    最大偏移值
 * @return int offset
 */
fun calcOffset(offset: Float, min: Float, max: Float): Float {
    return if (offset > max) {
        max
    } else if (offset < min) {
        min
    } else {
        offset
    }
}

/**
 *
 * @param child     需要移动的View
 * @param dy        实际偏移量
 * @param minOffset 最小偏移量
 * @param maxOffset 最大偏移量
 * @return void
 */
fun calcAndScroll(child: View, dy: Float, minOffset: Float, maxOffset: Float) {
    val initOffset = child.top
    val offset: Float = calcOffset(initOffset - dy, minOffset, maxOffset) - initOffset
    child.offsetTopAndBottom(offset.toInt())
}

/**
 * 得到TouchSlop 滑动距离的常量
 *
 * @param context 上下文
 * @return int touchSlop的具体值
 */
fun getTouchSlop(context: Context): Int {
    return ViewConfiguration.get(context).scaledTouchSlop
}

/**
 * view向下滑动
 */
fun moveDownSmooth(view : View,value : Int,time : Int = 500){
    view.isClickable = false
    ValueAnimator.ofInt(0,value).apply {
        duration = time.toLong()
        addUpdateListener {
            val adjustedHeight = it.animatedValue as Int
            view.layoutParams.height = adjustedHeight
            view.requestLayout()
        }
        doOnEnd {
            view.isClickable = true
        }
        interpolator = AccelerateDecelerateInterpolator()
    }.start()
}

/**
 * view向下滑动
 */
fun moveUpSmooth(view : View,value : Int,time : Int = 500){
    view.isClickable = false
    ValueAnimator.ofInt(value,0).apply {
        duration = time.toLong()
        addUpdateListener {
            val adjustedHeight = it.animatedValue as Int
            view.layoutParams.height = adjustedHeight
            view.requestLayout()
        }
        doOnEnd {
            view.isClickable = true
        }
        interpolator = AccelerateDecelerateInterpolator()
    }.start()
}


/**
 * 手势点击
 */
private fun clickPointer(any : Any,x : Float,y : Float){
    val downEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, x, y, 0);
    val upEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, x, y, 0);
    // 处理和分发
    if (any is View) {
        any.onTouchEvent(downEvent)
        any.onTouchEvent(upEvent)
    } else if (any is Activity) {
        any.dispatchTouchEvent(downEvent);
        any.dispatchTouchEvent(upEvent);
    }
    // 事件回收
    downEvent.recycle();
    upEvent.recycle();
}

/**
 * View点击
 */
private fun moveView(view : View,x : Float,y : Float){
    clickPointer(view,x,y)
}

/**
 * 手势滑动
 */
fun movePointer(view : View,downTime : Long,startX : Float ,startY : Float,endX : Float,endY : Float,duration : Long, period : Int){

        val handler = ViewHandler(view);
        view.onTouchEvent(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, startX, startY, 0));
        val bean = GestureBean(startX, startY, endX, endY, duration, period);
        Message.obtain(handler, 1, bean).sendToTarget();

}