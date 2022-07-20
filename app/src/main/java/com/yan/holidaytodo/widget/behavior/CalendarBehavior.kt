package com.yan.holidaytodo.widget.behavior

import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.widget.MonthView


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget.behavior
 * @ClassName:      CalendarBehavior
 * @Author:         Yan
 * @CreateDate:     2022年07月20日 18:42:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    日历Behavior
 */
class CalendarBehavior : CoordinatorLayout.Behavior<MonthView>() {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: MonthView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int,
    ): Boolean {
//        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
        Log.e("isMoved3","true")
        return true
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: MonthView,
        ev: MotionEvent,
    ): Boolean {
        Log.e("isMoved3","true")
        return true
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: MonthView,
        dependency: View,
    ): Boolean {
        Log.e("isMoved1","true")
//        movePointer(child,50,5f,5f,2f,2f,10,2)
        child.y = dependency.y
        return true
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: MonthView,
        dependency: View,
    ): Boolean {
        Log.e("isMoved2","true")
        return dependency is RecyclerView
    }
}
