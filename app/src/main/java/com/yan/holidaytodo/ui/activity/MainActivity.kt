package com.yan.holidaytodo.ui.activity

import android.os.Bundle
import com.yan.common.base.BaseActivity
import com.yan.holidaytodo.R
import com.yan.holidaytodo.adapter.CalendarAdapter
import com.yan.holidaytodo.bean.CalendarAttr
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.ui.viewmodel.HomeViewModel
import com.yan.holidaytodo.widget.CalendarView
import com.yan.holidaytodo.widget.CustomDayView
import com.yan.holidaytodo.widget.MonthView
import com.yan.holidaytodo.widget.SingleDay


class MainActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        findViewById<CalendarView>(R.id.calendar).apply {
//            initAttr(CalendarAttr(CalendarAttr.CalendarType.MONTH))
//            initOnSelectListener (object : OnSelectDateListener {
//                override fun onSelectDate(calendarData: CalendarData) {
//
//                }
//
//                override fun onSelectOtherMonth(offset: Int) {
//
//                }
//
//            })
//            initDayDrawer(CustomDayView(baseContext,R.layout.custiom_day))
//            initCurrentPosition(1000)
//        }
        findViewById<MonthView>(R.id.month_view).apply {
            initAdapter(CalendarAdapter(context, object : OnSelectDateListener {
                override fun onSelectDate(calendarData: CalendarData) {

                }

                override fun onSelectOtherMonth(offset: Int) {

                }

            },CustomDayView(baseContext,R.layout.custiom_day)))
        }
    }


}