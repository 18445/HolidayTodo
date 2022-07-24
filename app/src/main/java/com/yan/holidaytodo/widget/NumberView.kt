package com.yan.holidaytodo.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.yan.holidaytodo.R


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      NumberView
 * @Author:         Yan
 * @CreateDate:     2022年07月24日 19:44:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    带有动画效果的数字
 */
class NumberView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object{

        //数字位置
        const val POSITION_ONE = "POSITION_ONE"
        const val POSITION_TWO = "POSITION_TWO"
        const val POSITION_THREE = "POSITION_THREE"
        const val POSITION_FOUR = "POSITION_FOUR"
        const val POSITION_FIVE = "POSITION_FIVE"
        const val POSITION_SIX = "POSITION_SIX"

        //变化模式
        const val UP_ANIMATOR_MODE = 0x12345
        const val DOWN_ANIMATOR_MODE = 0x54321

        //延迟时间
        private const val DELAY_DURATION = 200

        private const val DURATION = 400

    }

    private var freshMode = UP_ANIMATOR_MODE

    private var mTvFirst : TextView
    private var mTvSecond : TextView

    private var mWidth = 0
    private var mHeight = 0
    private var textSize = 0
    private var mCurrentValue = 0
    private var mTrueValue = 0



    private lateinit var containerLp: LinearLayout.LayoutParams
    private lateinit var tvFirstLp: LayoutParams
    private lateinit var tvSecondLp: MarginLayoutParams

    private lateinit var mNumberAnim : ValueAnimator


    init {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_progress_number_item_layout,this,true)
        mTvFirst = findViewById(R.id.tv_number_one)
        mTvSecond = findViewById(R.id.tv_number_two)
        initAnim()
    }

    private fun initAnim(){
        mNumberAnim = ValueAnimator.ofFloat(0F,1F).apply {
            duration = DURATION.toLong()
            interpolator = OvershootInterpolator()
            repeatCount = 0
            repeatMode = ValueAnimator.RESTART
            addUpdateListener {
                val percent = it.animatedValue as Float
                if (freshMode == UP_ANIMATOR_MODE){
                    mTvFirst.translationY = (-mHeight * percent)
                    mTvSecond.translationY = (-mHeight * percent)
                }else{
                    mTvFirst.translationY = (mHeight * percent)
                    mTvSecond.translationY = (-2 * mHeight + mHeight * percent)
                }
            }
        }
    }

    /**
     * 设置数字
     */
    fun setCurrentNumber(position : String,value : Int,trueValue : Int){
        freshMode = if (trueValue > mTrueValue) {
            UP_ANIMATOR_MODE
        } else {
            DOWN_ANIMATOR_MODE
        }
        mTrueValue = trueValue
        setCurrentNextValue(position,value,freshMode)
    }

    /**
     * 设置下一位的值
     */
    fun setCurrentNextValue(position : String,value : Int,mMode : Int){
        if (value == mCurrentValue) {
            return
        }
        freshMode = mMode
        val startDelay = setStartDelay(position)
        //判断数字是增加还是减少，进而确定不同的动画效果
        mTvFirst.text = mCurrentValue.toString()
        mTvSecond.text = value.toString()
        if (mNumberAnim.isRunning) {
            mNumberAnim.cancel()
        }
        mNumberAnim.duration = (400 + startDelay).toLong()
        mTvFirst.translationY = 0f
        mTvSecond.translationY = 0f
        mNumberAnim.start()
        mCurrentValue = value
    }


    private fun setStartDelay(position : String) : Int{
        val startDelay =
        when (position) {
            POSITION_ONE -> {
                0
            }
            POSITION_TWO -> {
                DELAY_DURATION
            }
            POSITION_THREE -> {
                DELAY_DURATION * 2
            }
            POSITION_FOUR -> {
                DELAY_DURATION * 3
            }
            POSITION_FIVE -> {
                DELAY_DURATION * 4
            }
            POSITION_SIX -> {
                DELAY_DURATION * 5
            }
            else -> {
                0
            }
        }

        return startDelay

    }

    /**
     * 设置文字属性
     */
    fun setLayoutSize(w: Int, h: Int, s: Int) {
        mHeight = h
        mWidth = w

        textSize = s
        containerLp = LinearLayout.LayoutParams(mWidth, mHeight)
        layoutParams = containerLp
        tvFirstLp = LayoutParams(mWidth, mHeight)
        mTvFirst.layoutParams = tvFirstLp
        mTvFirst.gravity = Gravity.CENTER
        mTvFirst.textSize = textSize.toFloat()

        tvSecondLp = LayoutParams(mWidth, mHeight)
        tvSecondLp.topMargin = mHeight
        mTvSecond.layoutParams = tvSecondLp
        mTvSecond.gravity = Gravity.CENTER
        mTvSecond.textSize = textSize.toFloat()

    }

}