package com.yan.holidaytodo.ui.activity

import android.os.Bundle
import androidx.core.view.isVisible
import com.yan.common.base.BaseActivity
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.CalendarAttr
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.callback.OnAdapterSelectListener
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.ui.viewmodel.HomeViewModel
import com.yan.holidaytodo.widget.CalendarWeekView
import com.yan.holidaytodo.widget.CustomDayView
import com.yan.holidaytodo.widget.MonthView
import java.time.Month


class MainActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarWeekView = findViewById<CalendarWeekView>(R.id.calendar_week).apply {
            initOnSelectListener(object : OnSelectDateListener{
                override fun onSelectDate(calendarData: CalendarData,row  : Int,col : Int) {

                }

                override fun onSelectOtherMonth(offset: Int) {
                }

            })
            initCurrentPosition(1000)
            initDayDrawer(CustomDayView(context,R.layout.custiom_day))
        }

        findViewById<MonthView>(R.id.month_view).apply {
            initAdapter(context, onSelectedDateHide = {
                calendarWeekView.isVisible = it
            },calendarWeekView,CustomDayView(baseContext, R.layout.custiom_day))
        }




    }
}