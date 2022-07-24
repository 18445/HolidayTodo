package com.yan.holidaytodo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.yan.holidaytodo.util.dpToPx


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        layout
 * @ClassName:      ProgressLayout
 * @Author:         Yan
 * @CreateDate:     2022年07月24日 22:08:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    扇形粒子和数字切换组合
 */
class ProgressLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var circleProgressView :CircleProgressView
    private var mAnimNumberView: AnimNumberView
    private val mHeight = 328.dpToPx()
    private val mWidth = 328.dpToPx()
    private val outerShaderWidth = 10.dpToPx()
    private val circleStrokeWidth = 13.dpToPx()

    private var countMode = false


    init {
        setMeasuredDimension(mWidth, mHeight)
        circleProgressView = CircleProgressView(getContext(), mWidth, mHeight, outerShaderWidth, circleStrokeWidth)
        val progressViewLp = LayoutParams(mWidth, mHeight)
        progressViewLp.gravity = Gravity.CENTER
        circleProgressView.layoutParams = progressViewLp

        mAnimNumberView = AnimNumberView(getContext())
        val animNumberViewLp = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        animNumberViewLp.gravity = Gravity.CENTER
        mAnimNumberView.layoutParams = animNumberViewLp

        addView(circleProgressView)
        addView(mAnimNumberView)
    }

    /**
     * 设置正计时 or 倒计时
     * 如果是正计时传入second>0表示设置了目标时间，达到时间停止计时并回调到达的转态
     * 如果是倒计时，计时结束回调计时结束的状态
     */
    fun setTimer(second: Int, timerMode: Int,onComplete: () -> Unit) {
        circleProgressView.setWareMode()
        mAnimNumberView.setTimer(second, timerMode)
        mAnimNumberView.setOnTimerCompleteListener (object : AnimNumberView.OnTimerComplete {
            override fun onComplete() {
                onComplete.invoke()
            }
        })
    }

    /**
     * 设置倒计时模式
     *
     * @param second
     */
    fun setCountMode(second: Int, onComplete: () -> Unit) {
        countMode = true
        circleProgressView.setCountMode(second)
        mAnimNumberView.setTimer(second, AnimNumberView.DOWN_TIMER)
        mAnimNumberView.setOnTimerCompleteListener (object : AnimNumberView.OnTimerComplete {
            override fun onComplete() {
                onComplete.invoke()
            }
        })
    }

    /**
     * 设置当前温度
     */
    fun setTemperature(
        current: Float,
        target: Float,
    ) {
        countMode = false
        //变色环设置温度
        circleProgressView.countMode = false
        circleProgressView.setCurrentProgress(current, target)
        //设置温度模式
        mAnimNumberView.setTemperature(current.toInt(), AnimNumberView.TEMPERATURE_MODE)
    }

}