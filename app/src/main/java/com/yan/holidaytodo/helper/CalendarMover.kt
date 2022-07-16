package com.yan.holidaytodo.helper

import android.animation.ValueAnimator
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import com.yan.holidaytodo.util.dpToPx
import com.yan.holidaytodo.widget.CalendarView

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.helper
 * @ClassName:      CalendarMover
 * @Author:         Yan
 * @CreateDate:     2022年07月16日 16:59:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    负责日历的移动
 */
class CalendarMover(private val calendarView: CalendarView) {


    /**
     * 提供给外部的滑动方法
     */
    fun calendarMove(calendarState: CalendarState,offsetY : Float){
        Log.d("beMoved",offsetY.toString())
        Log.d("beMoved",calendarView.layoutParams.height.toString())
        Log.d("beMoved",calendarState.name)
        Log.d("beMoved",calendarView.layoutParams.height.toString())
        Log.d("beMoved",calendarView.mNormalHeight.toString())
        if(calendarState === CalendarState.NORMAL && offsetY > 0){
            //向下滑动
            calendarNormalDown(offsetY)
        }else if(calendarState === CalendarState.NORMAL && offsetY < 0){
            //向上滑动
            calendarNormalUp(offsetY)
        }else if(calendarState === CalendarState.STRETCHING && offsetY < 0
            && calendarView.mCurrentHeight <= calendarView.mMostHeight.dpToPx()
            && calendarView.mCurrentHeight > calendarView.mNormalHeight){
            Log.d("beMoved", calendarState.name)
            calendarStretchingUp(offsetY)
        }
    }


    /**
     * 普通状态向上滑动
     */
    private fun calendarNormalUp(offsetY: Float){
        calendarView.apply {
            layoutParams.height += offsetY.toInt()
            requestLayout()
            downPercent = -(layoutParams.height - mNormalHeight )/(mLeastHeight.dpToPx().toFloat() - mNormalHeight)
        }
    }


    /**
     * 普通状态向下滑动
     */
    private fun calendarNormalDown(offsetY : Float){
        calendarView.apply {
            layoutParams.height += offsetY.toInt()
            requestLayout()
            downPercent = (layoutParams.height - mNormalHeight )/(mMostHeight.dpToPx().toFloat() - mNormalHeight)
        }
    }

    /**
     * 拉伸状态拉到普通
     */
    private fun calendarStretchingUp(offsetY: Float){
        calendarView.apply {
            layoutParams.height += offsetY.toInt()
            requestLayout()
            downPercent = (layoutParams.height - mNormalHeight )/(mMostHeight.dpToPx().toFloat() - mNormalHeight)
        }
    }



    /**
     * 普通状态滑倒底部
     */
    fun moveToDown(){
        calendarView.apply {
            val time = (1 - downPercent) * 500 + 500
            ValueAnimator.ofInt(mCurrentHeight,mMostHeight.dpToPx()).apply {
                duration = time.toLong()
                addUpdateListener {
                    val adjustedHeight = it.animatedValue as Int
                    layoutParams.height = adjustedHeight
                    requestLayout()
                    downPercent = (layoutParams.height - mNormalHeight )/(mMostHeight.dpToPx().toFloat() - mNormalHeight)
                }
                interpolator = AccelerateDecelerateInterpolator()
            }.start()
        }
    }

    /**
     * 滑动成正常状态
     */
    fun moveToNormal(){
        calendarView.apply {
            val time =  downPercent * 500 + 500
            ValueAnimator.ofInt(mCurrentHeight,mNormalHeight).apply {

                duration = time.toLong()
                addUpdateListener {
                    val adjustedHeight = it.animatedValue as Int
                    layoutParams.height = adjustedHeight
                    requestLayout()
                    downPercent = (layoutParams.height - mNormalHeight )/(mMostHeight.dpToPx().toFloat() - mNormalHeight)
                }
                interpolator = AccelerateDecelerateInterpolator()
            }.start()
        }
    }







    enum class CalendarState {
        //普通状态
        NORMAL,
        //折叠状态
        FOLDING,
        //拉伸
        STRETCHING
    }
}