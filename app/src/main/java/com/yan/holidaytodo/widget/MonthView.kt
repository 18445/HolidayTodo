package com.yan.holidaytodo.widget

import android.content.Context
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import com.yan.holidaytodo.callback.ICalendarView

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      MonthView
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 17:57:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
class MonthView(context: Context) : LinearLayout(context),ICalendarView {

    companion object{
        var CURRENT_DAY_INDEX = 1000
        var currentPosition = CURRENT_DAY_INDEX
    }


    val monthViewPager = ViewPager2(context)
    val scrollState = ViewPager2.SCROLL_STATE_IDLE

    override fun backToday() {
        TODO("Not yet implemented")
    }

    override fun currentIdx() {
        TODO("Not yet implemented")
    }

    override fun focusCalendar() {
        TODO("Not yet implemented")
    }

    override fun reDraw() {
        TODO("Not yet implemented")
    }
}