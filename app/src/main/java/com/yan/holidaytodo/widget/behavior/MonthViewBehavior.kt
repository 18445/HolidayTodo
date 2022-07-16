package com.yan.holidaytodo.widget.behavior

import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.widget.MonthView

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget.behavior
 * @ClassName:      MonthViewBehavior
 * @Author:         Yan
 * @CreateDate:     2022年07月16日 01:39:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    MonthView的behavior
 */
class MonthViewBehavior : CoordinatorLayout.Behavior<MonthView>() {


    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: MonthView,
        dependency: View,
    ): Boolean {
        return dependency is RecyclerView
    }


    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: MonthView,
        layoutDirection: Int,
    ): Boolean {
        return super.onLayoutChild(parent, child, layoutDirection)
    }


    override fun onTouchEvent(
        parent: CoordinatorLayout,
        child: MonthView,
        ev: MotionEvent,
    ): Boolean {
        return super.onTouchEvent(parent, child, ev)
    }


    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: MonthView,
        ev: MotionEvent,
    ): Boolean {
        return super.onInterceptTouchEvent(parent, child, ev)
    }


    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: MonthView,
        dependency: View,
    ): Boolean {
        return super.onDependentViewChanged(parent, child, dependency)
    }


}