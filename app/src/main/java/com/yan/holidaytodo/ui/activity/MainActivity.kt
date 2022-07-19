package com.yan.holidaytodo.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toolbar
import androidx.core.view.isVisible
import com.yan.holidaytodo.base.BaseActivity
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.State
import com.yan.holidaytodo.bean.view.CalendarAttr
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.ui.viewmodel.HomeViewModel
import com.yan.holidaytodo.util.getDay
import com.yan.holidaytodo.util.getMonth
import com.yan.holidaytodo.util.getYear
import com.yan.holidaytodo.widget.CalendarWeekView
import com.yan.holidaytodo.widget.CustomDayView
import com.yan.holidaytodo.widget.MonthView


class MainActivity : BaseActivity<HomeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.tb_main).apply {
            viewModel.onSelectedDate(CalendarData(getYear(), getMonth(), getDay()))
        }

        viewModel.todayDate.observe(this){
            toolbar.title = it
        }

        viewModel.getYearInfo("2022")
        viewModel.observeYearInfo(this){
            onSuccess {
                Log.e("onSuccessYearInfo",it.toString())
            }
        }

        viewModel.getWorkdayNext("2022-7-11")
        viewModel.observeWorkdayNext(this){
            onSuccess {
                Log.e("3",it.toString())
            }
        }

        viewModel.getHolidayNext("2022-7-11")
        viewModel.observeHolidayNext(this){
            onSuccess {
                Log.e("onSuccessHolidayNext",it.toString())
            }
        }

        viewModel.getDayInfo("2022-7-11")
        viewModel.observeDayInfo(this){
            onSuccess {
                Log.e("onSuccessDayInfo",it.toString())
            }
        }

        val calendarWeekView = findViewById<CalendarWeekView>(R.id.calendar_week).apply {
            initOnSelectListener(object : OnSelectDateListener{

                override fun onSelectDate(
                    calendarData: CalendarData,
                    row: Int,
                    col: Int,
                    type: CalendarAttr.CalendarType,
                    state: State,
                ) {
                }

                override fun onSelectOtherMonth(offset: Int) {
                }

            })
            initDayDrawer(CustomDayView(context,R.layout.custiom_day))
        }

        findViewById<MonthView>(R.id.month_view).apply {
            initAdapter(onSelectDateListener = object : OnSelectDateListener{
                override fun onSelectDate(
                    calendarData: CalendarData,
                    row: Int,
                    col: Int,
                    type: CalendarAttr.CalendarType,
                    state: State,
                ) {
                    viewModel.onSelectedDate(calendarData)
                }

                override fun onSelectOtherMonth(offset: Int) {

                }

            }, onSelectedDateHide = {
                calendarWeekView.isVisible = it
            }, calendarWeekView, CustomDayView(baseContext, R.layout.custiom_day))
        }




    }
}