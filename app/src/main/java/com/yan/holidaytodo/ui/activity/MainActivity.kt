package com.yan.holidaytodo.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yan.common.base.BaseActivity
import com.yan.holidaytodo.R
import com.yan.holidaytodo.adapter.CalendarAdapter
import com.yan.holidaytodo.adapter.TodoAdapter
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

        findViewById<MonthView>(R.id.month_view).apply {
            initAdapter(context,CustomDayView(baseContext,R.layout.custiom_day))
        }

    }
}