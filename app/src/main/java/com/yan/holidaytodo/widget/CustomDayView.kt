package com.yan.holidaytodo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.bean.State
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.util.getDay
import com.yan.holidaytodo.util.getMonth
import com.yan.holidaytodo.util.getYear

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      CustomDayView
 * @Author:         Yan
 * @CreateDate:     2022年07月15日 03:13:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:     //TODO
 */
@SuppressLint("ViewConstructor")
class CustomDayView(context: Context, layoutResource: Int) :
    DayView(context, layoutResource) {
    private val dateTv: TextView = findViewById(R.id.date)
    private val marker: ImageView = findViewById(R.id.maker)
    private val selectedBackground: View = findViewById(R.id.selected_background)
    private val todayBackground: View = findViewById(R.id.today_background)
    private val today: CalendarData = CalendarData(getYear(), getMonth(), getDay())

    override fun refreshContent() {
        drawerToday(day.data)
        drawerSelect(day.state)
        //恢复
        super.refreshContent()
//        drawerMarker(day.getDate(), day.getState())
    }

//    private fun drawerMarker(data: CalendarData, state: State) {
//        if (loadMarkData().containsKey(date.toString())) {
//            if (state === State.SELECT || date.toString().equals(today.toString())) {
//                marker.visibility = GONE
//            } else {
//                marker.visibility = VISIBLE
//                if (Utils.loadMarkData().get(date.toString()).equals("0")) {
//                    marker.isEnabled = true
//                } else {
//                    marker.isEnabled = false
//                }
//            }
//        } else {
//            marker.visibility = GONE
//        }
//    }

    private fun drawerSelect(state: State) {
        if (state === State.SELECT) {
            selectedBackground.visibility = VISIBLE
            dateTv.setTextColor(Color.WHITE)
        } else if (state === State.NEXT_MONTH || state === State.PAST_MONTH) {
            selectedBackground.visibility = GONE
            dateTv.setTextColor(Color.parseColor("#d5d5d5"))
        } else {
            selectedBackground.visibility = GONE
            dateTv.setTextColor(Color.parseColor("#111111"))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun drawerToday(date: CalendarData) {
        if (date == today) {
            dateTv.text = "今"
            todayBackground.visibility = VISIBLE
        } else {
            dateTv.text = date.day.toString() + ""
            todayBackground.visibility = GONE
        }
    }

}
