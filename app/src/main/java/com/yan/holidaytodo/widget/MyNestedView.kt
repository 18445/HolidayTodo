package com.yan.holidaytodo.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent3
import androidx.recyclerview.widget.RecyclerView
import com.yan.holidaytodo.helper.CalendarMover
import com.yan.holidaytodo.util.dpToPx

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

    private var mDiffRawY = 0

    private var mLastRawY = 0

    //判断是否RV移动了
    private var isMoved = false

    //判断整体是否是上移了 解决上移不回复的BUG
    private var isMoveUp = false

    //判断View的状态
    private var mState = CalendarMover.CalendarState.NORMAL

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

            if (mState === CalendarMover.CalendarState.FOLDING && mDiffRawY < 0){
//                isMoveUp = false
//                isMoved = true
                return
            }

            if( mMonthView.height > 125 && mState === CalendarMover.CalendarState.NORMAL && mDiffRawY < 0){ //普通状态向上
                mMonthView.moveOffset(mDiffRawY)
                consumed[1] = dy
                isMoveUp = true
            }else if (mMonthView.height < 850 && mState === CalendarMover.CalendarState.FOLDING && mDiffRawY > 0){ //折叠状态向下
                isMoveUp = false
                mMonthView.moveOffset(mDiffRawY)
                consumed[1] = dy
            }else if (mRecyclerView.scrollY == 0 && mState === CalendarMover.CalendarState.FOLDING){
                isMoveUp = false
                mMonthView.moveOffset(mDiffRawY)
//                consumed[1] = dy
//            }else if(mState === CalendarMover.CalendarState.FOLDING && mDiffRawY > 0){
//                isMoveUp = false
//                mMonthView.moveOffset(mDiffRawY)
//                consumed[1] = dy
            }else {
                isMoved = true
            }

    }


    private var mPosY = 0f

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        mDiffRawY = ev.rawY.toInt() - mLastRawY
        mLastRawY = ev.rawY.toInt()
        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                mPosY = ev.y
            }

            MotionEvent.ACTION_UP -> {
                val disY = ev.y - mPosY
                if (mRecyclerView.scrollY == 0 && disY < 0 && mState === CalendarMover.CalendarState.NORMAL && !isMoveUp){
                    isMoved = false
                    return super.dispatchTouchEvent(ev)
                }
                if (!isMoved || mRecyclerView.scrollY == 0){

                    val state = if(disY > 0 && mState === CalendarMover.CalendarState.NORMAL){
                        mMonthView.notifyClickDone(disY.toInt()/1.75f.toInt())
                    }else{
                        mMonthView.notifyClickDone(disY.toInt())
                    }
                    if(state != null){
                        mState = state
                    }
                }
                isMoveUp = false
                isMoved = false

            }
        }
        return super.dispatchTouchEvent(ev)
    }


}