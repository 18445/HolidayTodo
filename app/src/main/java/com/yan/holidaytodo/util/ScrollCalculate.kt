package com.yan.holidaytodo.util

import android.content.Context
import android.view.View
import android.view.ViewConfiguration
import android.widget.Scroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

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
fun calcOffset(offset: Int, min: Int, max: Int): Int {
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
fun scroll(child: View, dy: Int, minOffset: Int, maxOffset: Int) {
    val initOffset = child.top
    val offset: Int = calcOffset(initOffset - dy, minOffset, maxOffset) - initOffset
    child.offsetTopAndBottom(offset)
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

var ScrollToBottom = false

/**
 * 判断上一次滑动改变周月日历是向下滑还是向上滑 向下滑表示切换为月日历模式 向上滑表示切换为周日历模式
 *
 * @return boolean 是否是在向下滑动。(true: 已经收缩; false: 已经打开）
 */
fun isCalendarScrollToBottom(): Boolean {
    return ScrollToBottom
}

/**
 * 设置上一次滑动改变周月日历是向下滑还是向上滑 向下滑表示切换为月日历模式 向上滑表示切换为周日历模式
 *
 */
fun setCalendarScrollToBottom(ifScrollToBottom: Boolean) {
    ScrollToBottom = ifScrollToBottom
}

    var top : Int = 0


/**
 * 通过scrollTo方法完成协调布局的滑动，其中主要使用了ViewCompat.postOnAnimation
 *
 * @param parent   协调布局parent
 * @param child    协调布局协调滑动的child
 * @param y        滑动目标位置y轴数值
 * @param duration 滑动执行时间
 * @return void
 */
fun calendarScrollTo(parent: CoordinatorLayout, child: RecyclerView, y: Int, duration: Int) {
    val scroller = Scroller(parent.context)
    scroller.startScroll(
        0,
        top,
        0,
        y - top,
        duration
    ) //设置scroller的滚动偏移量
    ViewCompat.postOnAnimation(child, object : Runnable {
        override fun run() {
            //返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成。
            //用来判断是否滚动是否结束
            if (scroller.computeScrollOffset()) {
                val delta: Int = scroller.currY - child.top
                child.offsetTopAndBottom(delta)
                top = (child.top)
                parent.dispatchDependentViewsChanged(child)
                ViewCompat.postOnAnimation(child, this)
            }
        }
    })
}


