package com.yan.holidaytodo.helper

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
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

    //view移动的距离
    private var mScrollOffset = 0f

    //判断是否已经向下
    //解决向下滑动时能向上滑动带着整体View一起向下的BUG
    private var hasDown = false


    /**
     * 提供给外部的滑动方法
     */
    fun calendarMove(calendarState: CalendarState, offsetY: Float) {

        if (calendarState === CalendarState.NORMAL && offsetY > 0 && calendarView.mCurrentHeight + offsetY <= calendarView.mNormalHeight && !hasDown ) {
            //向上滑动时向下滑动
            calendarNormalDownWhenUp(offsetY)
        } else if (calendarState === CalendarState.NORMAL && offsetY < 0 && calendarView.mCurrentHeight  <= calendarView.mNormalHeight && !hasDown) {
            //向上滑动
            calendarNormalUp(offsetY)
        } else if (calendarState === CalendarState.NORMAL && calendarView.mCurrentHeight >= calendarView.mNormalHeight ) {
            //向下滑动
            hasDown = true
            calendarNormalDown(offsetY)
        } else if (calendarState === CalendarState.STRETCHING
            && calendarView.mCurrentHeight <= calendarView.mMostHeight.dpToPx()
            && calendarView.mCurrentHeight > calendarView.mNormalHeight
        ) {
            calendarStretchingUp(offsetY)
        } else if (calendarState === CalendarState.FOLDING && offsetY > 0
            && calendarView.mCurrentHeight <= calendarView.mNormalHeight
            && calendarView.mCurrentHeight >= calendarView.mLeastHeight.dpToPx()
        ) {
            calendarFoldingDown(offsetY)
        }
    }

    /**
     * 普通状态向上滑动
     */
    private fun calendarNormalUp(offsetY: Float) {
        calendarView.apply {
            layoutParams.height += offsetY.toInt()
            requestLayout()
            mScrollOffset -= offsetY
            scrollTo(0, mScrollOffset.toInt())
        }
    }


    /**
     * 普通状态向下滑动
     */
    private fun calendarNormalDown(offsetY: Float) {
        calendarView.apply {
            layoutParams.height += offsetY.toInt()
            downPercent = (layoutParams.height - mNormalHeight) / (mMostHeight.dpToPx()
                .toFloat() - mNormalHeight)
            requestLayout()
        }
    }

    /**
     * 普通状态上拉时下滑
     */
    private fun calendarNormalDownWhenUp(offsetY: Float) {
        calendarView.apply {
            layoutParams.height += offsetY.toInt()
            requestLayout()
            mScrollOffset -= offsetY
            scrollTo(0, mScrollOffset.toInt())
        }
    }

    /**
     * 拉伸状态向上滑动
     */
    private fun calendarStretchingUp(offsetY: Float) {
        calendarView.apply {
            layoutParams.height += offsetY.toInt()
            requestLayout()
            downPercent = (layoutParams.height - mNormalHeight) / (mMostHeight.dpToPx()
                .toFloat() - mNormalHeight)
        }
    }

    /**
     * 收缩状态向下滑动
     */
    private fun calendarFoldingDown(offsetY: Float) {
        calendarView.apply {
            layoutParams.height += offsetY.toInt()
            requestLayout()
            mScrollOffset -= offsetY
            scrollTo(0, mScrollOffset.toInt())
        }
    }


    /**
     * 普通状态滑倒底部
     */
    fun moveToDown() {
        calendarView.apply {
            val time = (1 - downPercent) * 500 + 500
            ValueAnimator.ofInt(mCurrentHeight, mMostHeight.dpToPx()).apply {
                duration = time.toLong()
                addUpdateListener {
                    val adjustedHeight = it.animatedValue as Int
                    layoutParams.height = adjustedHeight
                    requestLayout()
                    downPercent = (layoutParams.height - mNormalHeight) / (mMostHeight.dpToPx()
                        .toFloat() - mNormalHeight)
                }
                interpolator = AccelerateDecelerateInterpolator()
            }.start()
        }
    }

    /**
     * 滑动成正常状态
     */
    fun moveToNormal() {
        calendarView.apply {
            val time = downPercent * 500 + 500
            ValueAnimator.ofInt(mCurrentHeight, mNormalHeight).apply {

                duration = time.toLong()
                addUpdateListener {
                    val adjustedHeight = it.animatedValue as Int
                    layoutParams.height = adjustedHeight
                    requestLayout()
                    downPercent = (layoutParams.height - mNormalHeight) / (mMostHeight.dpToPx()
                        .toFloat() - mNormalHeight)
                }
                interpolator = AccelerateDecelerateInterpolator()
            }.start()
        }
    }

    /**
     * 上滑时向下滑动成正常状态
     */
    fun moveToNormalWhenUp() {
        calendarView.apply {
            val time = 500
            val v1 = ValueAnimator.ofInt(mCurrentHeight, mNormalHeight).apply {
                duration = time.toLong()
                addUpdateListener {
                    val adjustedHeight = it.animatedValue as Int
                    layoutParams.height = adjustedHeight
                    requestLayout()
                }
                interpolator = AccelerateDecelerateInterpolator()
            }
            val v2 = ValueAnimator.ofInt(mScrollOffset.toInt(), 0).apply {
                duration = time.toLong()
                addUpdateListener {
                    val adjustedMove = it.animatedValue as Int
                    scrollTo(0, adjustedMove)
                }
                doOnEnd {
                    mScrollOffset = 0f
                }
                interpolator = AccelerateDecelerateInterpolator()
            }
            AnimatorSet().apply {
                play(v1).with(v2)
                start()
            }
        }
    }

    /**
     * 进入收缩状态
     */
    fun moveToFoldingTop() : Boolean{
        if(hasDown){
            moveToNormal()
            return false
        }
        calendarView.apply {
            val time = 250
            val v1 = ValueAnimator.ofInt(mCurrentHeight, mLeastHeight.dpToPx()).apply {
                duration = time.toLong()
                addUpdateListener {
                    val adjustedHeight = it.animatedValue as Int
                    layoutParams.height = adjustedHeight
                    requestLayout()
                }
                interpolator = AccelerateDecelerateInterpolator()
            }
            val v2 = ValueAnimator.ofInt(mScrollOffset.toInt(), 700).apply {
                duration = time.toLong()
                addUpdateListener {
                    val adjustedMove = it.animatedValue as Int
                    scrollTo(0, adjustedMove)
                }
                doOnEnd {
                    mScrollOffset = 700f
                }
                interpolator = AccelerateDecelerateInterpolator()
            }
            AnimatorSet().apply {
                play(v1).with(v2)
                start()
            }
        }
        return true
    }

    /**
     * 收缩状态恢复普通
     */
    fun moveToNormalWhenFolding() {
        calendarView.apply {
            val time = 500
            val v1 = ValueAnimator.ofInt(mCurrentHeight, mNormalHeight).apply {
                duration = time.toLong()
                addUpdateListener {
                    val adjustedHeight = it.animatedValue as Int
                    layoutParams.height = adjustedHeight
                    requestLayout()
                }
                interpolator = AccelerateDecelerateInterpolator()
            }
            val v2 = ValueAnimator.ofInt(mScrollOffset.toInt(), 0).apply {
                duration = time.toLong()
                addUpdateListener {
                    val adjustedMove = it.animatedValue as Int
                    scrollTo(0, adjustedMove)
                }
                doOnEnd {
                    mScrollOffset = 0f
                }
                interpolator = AccelerateDecelerateInterpolator()
            }
            AnimatorSet().apply {
                play(v1).with(v2)
                start()
            }
        }
    }

    /**
     * 完成一次滑动重置初始值
     */
    fun resetState(){
        hasDown = false
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