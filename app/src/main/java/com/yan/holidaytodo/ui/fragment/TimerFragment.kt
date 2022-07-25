package com.yan.holidaytodo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.yan.holidaytodo.R
import com.yan.holidaytodo.base.BaseFragment
import com.yan.holidaytodo.widget.AnimNumberView
import com.yan.holidaytodo.widget.ProgressLayout


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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = inflater.inflate(R.layout.fragment_timer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        mProgressLayout = view.findViewById<ProgressLayout?>(R.id.progress_layout_timer).apply {
            setTimer(500, AnimNumberView.TIMER_MODE) {}
        }

        view.findViewById<Button>(R.id.mBtnTest1).apply {
            setOnClickListener {
                mProgressLayout.setCountMode(100) {}
            }
        }
        view.findViewById<Button>(R.id.mBtnTest2).apply {
            setOnClickListener {
                mProgressLayout.setTimer(0, AnimNumberView.UP_TIMER) {}
            }
        }
        view.findViewById<Button>(R.id.mBtnTest3).apply {
            setOnClickListener {
                mProgressLayout.setTimer(20, AnimNumberView.DOWN_TIMER) {}
            }
        }
        view.findViewById<Button>(R.id.mBtnTest4).apply {
            setOnClickListener {
                mProgressLayout.setTimer(20, AnimNumberView.UP_TIMER) {}
            }
        }
    }


}