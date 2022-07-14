package com.yan.holidaytodo.ui.activity

import android.os.Bundle
import com.yan.common.base.BaseActivity
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.CalendarAttr
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.ui.viewmodel.HomeViewModel
import com.yan.holidaytodo.widget.CalendarView
import com.yan.holidaytodo.widget.CustomDayView
import com.yan.holidaytodo.widget.SingleDay


class MainActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<CalendarView>(R.id.calendar).apply {
            initOnSelectListener (object : OnSelectDateListener {
                override fun onSelectDate(calendarData: CalendarData) {

                }

                override fun onSelectOtherMonth(offset: Int) {

                }

            })
            initAttr(CalendarAttr())
            initDayDrawer(CustomDayView(baseContext,R.layout.custiom_day))
        }
    }


}