package com.yan.holidaytodo.ui.activity

import android.os.Bundle
import com.yan.common.base.BaseActivity
import com.yan.holidaytodo.R
import com.yan.holidaytodo.ui.viewmodel.HomeViewModel
import com.yan.holidaytodo.widget.CustomDayView
import com.yan.holidaytodo.widget.MonthView


class MainActivity : BaseActivity<HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<MonthView>(R.id.month_view).apply {
            initAdapter(context, CustomDayView(baseContext, R.layout.custiom_day))
        }

    }
}