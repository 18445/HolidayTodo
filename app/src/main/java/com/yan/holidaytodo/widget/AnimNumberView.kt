package com.yan.holidaytodo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.yan.holidaytodo.R
import com.yan.holidaytodo.util.dpToPx
import com.yan.holidaytodo.widget.NumberView.Companion.DOWN_ANIMATOR_MODE
import com.yan.holidaytodo.widget.NumberView.Companion.UP_ANIMATOR_MODE
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      AnimNumberView
 * @Author:         Yan
 * @CreateDate:     2022年07月24日 22:10:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    温度变化的控件
 */
class AnimNumberView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    companion object{

        /**
         * 温度模式
         */
        const val TEMPERATURE_MODE = 0x1234

        /**
         * 时间模式
         */
        const val TIMER_MODE = 0x2345

        /**
         * 正计时
         */
        const val UP_TIMER = 0x3456

        /**
         * 倒计时
         */
        const val DOWN_TIMER = 0x4567

    }

    private var mTimerDisposable: Disposable? = null
    private lateinit var mTimerListener: OnTimerComplete
    private var mMode = TIMER_MODE
    private var mSingleView: NumberView
    private var mTenView: NumberView
    private var mHundredView: NumberView
    private var mHour1View: NumberView
    private var mHour2View: NumberView
    private var mMinute1View: NumberView
    private var mMinute2View: NumberView
    private var mSecond1View: NumberView
    private var mSecond2View: NumberView
    private var mColon1View: TextView
    private var mColon2View: TextView
    private var mTempContainer: LinearLayout
    private var mClockContainer: LinearLayout
    private var mTvCircle: TextView
    private var numberWidth: Int = 46.dpToPx()
    private var numberHeight: Int = 95.dpToPx()
    private var numberTextSize = 80
    private var mCurrent = 0


    init {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_progress_temp_number_layout, this, true)
        mSingleView = findViewById(R.id.mSingleNumber)
        mTenView = findViewById(R.id.mTenNumber)
        mHundredView = findViewById(R.id.mHundredNumber)
        mHour1View = findViewById(R.id.mHour1)
        mHour2View = findViewById(R.id.mHour2)
        mMinute1View = findViewById(R.id.mMinute1)
        mMinute2View = findViewById(R.id.mMinute2)
        mSecond1View = findViewById(R.id.mSecond1)
        mSecond2View = findViewById(R.id.mSecond2)
        mColon1View = findViewById(R.id.mColon1)
        mColon2View = findViewById(R.id.mColon2)
        mTempContainer = findViewById(R.id.mTempNumberContainer)
        mClockContainer = findViewById(R.id.mClockContainer)
        mTvCircle = findViewById(R.id.tv_number_one)
        checkMode(mMode)
        setLayoutSize(numberWidth, numberHeight, numberTextSize)
    }


    /**
     * 设置温度
     */
    fun setTemperature(temperature: Int, mode: Int) {
        disposeTimer()
        mCurrent = temperature
        checkMode(mode)
        //设置控件的尺寸
        numberWidth = 46.dpToPx()
        numberHeight = 95.dpToPx()
        numberTextSize = 80
        setLayoutSize(numberWidth, numberHeight, numberTextSize)
        val mSingle = temperature % 10
        val mTen = temperature / 10 % 10
        val mHundred = temperature / 100 % 10
        if (temperature < 10) {
            mSingleView.visibility = VISIBLE
            mTenView.visibility = GONE
            mHundredView.visibility = GONE
        } else if (temperature < 100) {
            mSingleView.visibility = VISIBLE
            mTenView.visibility = VISIBLE
            mHundredView.visibility = GONE
        } else {
            mSingleView.visibility = VISIBLE
            mTenView.visibility = VISIBLE
            mHundredView.visibility = VISIBLE
        }
        mSingleView.setCurrentNumber(NumberView.POSITION_ONE, mSingle, temperature)
        mTenView.setCurrentNumber(NumberView.POSITION_TWO, mTen, temperature)
        mHundredView.setCurrentNumber(NumberView.POSITION_THREE, mHundred, temperature)
    }

    /**
     * 设计正计时/倒计时
     */
    fun setTimer(mSecond: Int, timerMode: Int) {
        var second = mSecond
        disposeTimer()
        checkMode(TIMER_MODE)
        if (timerMode == UP_TIMER && second <= 0) {
            //没有设置正计时的目标时间
            second = Int.MAX_VALUE / 2
        }
        if (timerMode == TIMER_MODE){
            setClock(mSecond,timerMode)
            return
        }
        val finalSecond = second
        mTimerDisposable = Flowable.intervalRange(0, (second + 1).toLong(), 0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { aLong: Long ->
                if (timerMode == UP_TIMER) {
                    setClock(aLong.toInt(), UP_ANIMATOR_MODE)
                }
                if (timerMode == DOWN_TIMER) {
                    setClock(finalSecond - aLong.toInt(), DOWN_ANIMATOR_MODE)
                }
            }
            .doOnComplete {
                disposeTimer()
                if (this::mTimerListener.isInitialized)
                mTimerListener.onComplete()
            }
            .subscribe()
    }

    /**
     * 设置时间 单位秒
     *
     * @param second
     */
    private fun setClock(second: Int, mode: Int) {
        val hour: Int = second / (60 * 60)
        val min: Int = (second - hour * 3600) / 60
        val sec: Int = second - hour * 3600 - min * 60
        //计算HH：MM：SS 6个位置的值
        val mHour1Value = hour / 10 % 10
        val mHour2Value = hour % 10
        val mMinute1Value = min / 10 % 10
        val mMinute2Value = min % 10
        val mSecond1Value = sec / 10 % 10
        val mSecond2Value = sec % 10
        if (mHour1Value > 0) {
            //展示六位数字
            numberWidth = 72
            numberHeight = 152
            numberTextSize = 64
            mHour1View.visibility = VISIBLE
            mHour2View.visibility = VISIBLE
            mColon1View.visibility = VISIBLE
            mMinute1View.visibility = VISIBLE
        } else if (mHour2Value > 0) {
            //展示五位数字
            numberWidth = 41.dpToPx()
            numberHeight = 85.dpToPx()
            numberTextSize = 72
            mHour1View.visibility = GONE
            mHour2View.visibility = VISIBLE
            mColon1View.visibility = VISIBLE
            mMinute1View.visibility = VISIBLE
        } else if (mMinute1Value > 0) {
            //展示四位数字
            numberWidth = 46.dpToPx()
            numberHeight = 95.dpToPx()
            numberTextSize = 40
            mHour1View.visibility = GONE
            mHour2View.visibility = GONE
            mColon1View.visibility = GONE
            mMinute1View.visibility = VISIBLE
        } else {
            //展示三位数字
            numberWidth = 56.dpToPx()
            numberHeight = 114.dpToPx()
            numberTextSize = 92
            mHour1View.visibility = GONE
            mHour2View.visibility = GONE
            mColon1View.visibility = GONE
            mMinute1View.visibility = GONE
        }
        setLayoutSize(numberWidth, numberHeight, numberTextSize)
        mHour1View.setCurrentNextValue(NumberView.POSITION_SIX, mHour1Value, mode)
        mHour2View.setCurrentNextValue(NumberView.POSITION_FIVE, mHour2Value, mode)
        mMinute1View.setCurrentNextValue(NumberView.POSITION_FOUR, mMinute1Value, mode)
        mMinute2View.setCurrentNextValue(NumberView.POSITION_THREE, mMinute2Value, mode)
        mSecond1View.setCurrentNextValue(NumberView.POSITION_TWO, mSecond1Value, mode)
        mSecond2View.setCurrentNextValue(NumberView.POSITION_ONE, mSecond2Value, mode)
    }

    /**
     * 设置字体
     */
    private fun setLayoutSize(width : Int, height : Int,textSize : Int){
        mSingleView.setLayoutSize(width, height, textSize)
        mTenView.setLayoutSize(width, height, textSize)
        mHundredView.setLayoutSize(width, height, textSize)
        mTvCircle.textSize = textSize.toFloat()

        mColon1View.textSize = textSize.toFloat()
        mColon2View.textSize = textSize.toFloat()
        mHour1View.setLayoutSize(width, height, textSize)
        mHour2View.setLayoutSize(width, height, textSize)
        mMinute1View.setLayoutSize(width, height, textSize)
        mMinute2View.setLayoutSize(width, height, textSize)
        mSecond1View.setLayoutSize(width, height, textSize)
        mSecond2View.setLayoutSize(width, height, textSize)
    }

    private fun checkMode(mode : Int){
        if (mode == mMode) {
            return
        }
        mMode = mode
        if (mode == TEMPERATURE_MODE) {
            mTempContainer.visibility = VISIBLE
            mClockContainer.visibility = GONE
        }
        if (mode == TIMER_MODE || mode == UP_TIMER || mode == DOWN_TIMER) {
            mTempContainer.visibility = GONE
            mClockContainer.visibility = VISIBLE
        }
    }

    interface OnTimerComplete {
        /**
         * 计时完成回调
         */
        fun onComplete()
    }

    /**
     * 解绑计时器
     */
    private fun disposeTimer() {
        if (mTimerDisposable!= null && !mTimerDisposable!!.isDisposed) {
            mTimerDisposable!!.dispose()
            mTimerDisposable = null
        }
    }

    /**
     * 设置计时完成的回调
     */
    fun setOnTimerCompleteListener(mTimerListener: OnTimerComplete) {
        this.mTimerListener = mTimerListener
    }
}