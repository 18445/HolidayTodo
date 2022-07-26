package com.yan.holidaytodo.ui.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yan.common.extension.toast
import com.yan.holidaytodo.R
import com.yan.holidaytodo.base.BaseFragment
import com.yan.holidaytodo.util.calCurrentTime
import com.yan.holidaytodo.util.doAnimateClose
import com.yan.holidaytodo.util.doAnimateOpen
import com.yan.holidaytodo.widget.AnimNumberView
import com.yan.holidaytodo.widget.ProgressLayout
import com.yan.holidaytodo.widget.TimeRollView
import java.util.*
import kotlin.math.cos
import kotlin.math.sin


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.ui.fragment
 * @ClassName:      TimerFragment
 * @Author:         Yan
 * @CreateDate:     2022年07月24日 23:43:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    计时器Fragment
 */
class TimerFragment : BaseFragment() {

    private lateinit var mProgressLayout: ProgressLayout
    private lateinit var mTimeRollView : TimeRollView
    private lateinit var mHandler : Handler
    private var mIsMenuOpen = false
    private val mTimer : Timer = Timer()

    private lateinit var mBtnItem1 : Button
    private lateinit var mBtnItem2 : Button
    private lateinit var mBtnItem3 : Button
    private var mTimeMode = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_timer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initTimer()
    }

    private fun initView(view: View) {
        mProgressLayout = view.findViewById(R.id.progress_layout_timer)
        mTimeRollView = view.findViewById(R.id.trv_timer)

        //时钟Button
        mBtnItem1 = view.findViewById<Button?>(R.id.btn_timer_item1).apply {
            setOnClickListener {
                mTimeMode = true
                mProgressLayout.setWaveTimer()
                mProgressLayout.setTimer(calCurrentTime() / 60,AnimNumberView.TIMER_MODE){}
                mProgressLayout.setWaveTimer()
            }
        }
        //正计时
        mBtnItem2 = view.findViewById<Button?>(R.id.btn_timer_item2).apply {
            setOnClickListener {
                mTimeMode = false
                mProgressLayout.setTimer(0,AnimNumberView.UP_TIMER){
                }
            }
        }
        //倒计时
        mBtnItem3 = view.findViewById<Button?>(R.id.btn_timer_item3).apply {
            setOnClickListener {
                mTimeMode = false
                mProgressLayout.setCountMode(mTimeRollView.getCurrentCircle()){
                    mTimeMode = true
                    toast("倒计时完成，正在切回时钟模式")
                }
            }
        }

        view.findViewById<FloatingActionButton>(R.id.fa_btn_timer).apply {
            setOnClickListener {
                if(mIsMenuOpen){
                    mIsMenuOpen = false
                    closeMenu()
                }else{
                    mIsMenuOpen = true
                    openMenu()
                }
            }
        }
    }

    private fun initTimer(){
        mHandler = object : Handler(Looper.myLooper()!!){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val currentTime = calCurrentTime()
                mProgressLayout.setTimer(currentTime / 60,AnimNumberView.TIMER_MODE){}
            }
        }
        mTimer.schedule(object : TimerTask() {
            override fun run() {
                if(mTimeMode)
                mHandler.sendMessage(Message())
            }
        },0,3000)
    }

    private fun openMenu(){
        doAnimateOpen(mBtnItem1,0,3,275,325)
        doAnimateOpen(mBtnItem2,1,3,275,325)
        doAnimateOpen(mBtnItem3,2,3,275,325)
    }

    private fun closeMenu(){
        doAnimateClose(mBtnItem1,0,3,275,325)
        doAnimateClose(mBtnItem2,1,3,275,325)
        doAnimateClose(mBtnItem3,2,3,275,325)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTimer.cancel()
    }



}