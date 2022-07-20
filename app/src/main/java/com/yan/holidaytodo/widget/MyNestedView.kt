package com.yan.holidaytodo.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent3
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      MyNestedView
 * @Author:         Yan
 * @CreateDate:     2022年07月20日 19:50:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    MyNestedView
 */
class MyNestedView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes),NestedScrollingParent3{


    private lateinit var mMonthView : MonthView

    private lateinit var mRecyclerView : RecyclerView

    private var mTotalOffset = 0

    private var mDiffRawY = 0

    private var mLastRawY = 0

    private val mShownHeight by lazy {
        mMonthView.height / CalendarView.TOTAL_ROW
    }

    fun initMonthView(monthView: MonthView){
        mMonthView = monthView
    }

    fun initRecyclerView(recyclerView: RecyclerView){
        mRecyclerView = recyclerView
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {

    }

    override fun onStopNestedScroll(target: View, type: Int) {

    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray,
    ) {

    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
    ) {

    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

//        if(mTotalOffset + dy < mShownHeight && !mMonthView.ifFold() && dy > 0){
//            mTotalOffset += dy
//            mMonthView.moveOffset(mDiffRawY)
//            consumed[1] = dy
//        }else if(dy < 0 && mMonthView.ifFold()){
//            mTotalOffset += dy
            mMonthView.moveOffset(mDiffRawY)
//        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        mDiffRawY = ev.rawY.toInt() - mLastRawY
        mLastRawY = ev.rawY.toInt()
        return super.dispatchTouchEvent(ev)
    }


}