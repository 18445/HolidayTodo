package com.yan.holidaytodo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yan.common.App
import com.yan.holidaytodo.R
import com.yan.holidaytodo.adapter.ExpandableRecyclerView
import com.yan.holidaytodo.base.BaseFragment
import com.yan.holidaytodo.bean.State
import com.yan.holidaytodo.bean.rv.ItemContext
import com.yan.holidaytodo.bean.rv.ItemTitle
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
import com.yan.holidaytodo.widget.MyNestedView
import kotlinx.coroutines.delay
import kotlinx.coroutines.newFixedThreadPoolContext

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.ui
 * @ClassName:      CalendarFragment
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 19:55:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    日历Fragment
 */
class CalendarFragment : BaseFragment() {

    private val viewModel : HomeViewModel by activityViewModels()

    private lateinit var calendarWeekView: CalendarWeekView

    private lateinit var monthView: MonthView

    private lateinit var recyclerView : RecyclerView

    private lateinit var myNestedView: MyNestedView

    private lateinit var dateListener : (CalendarData) -> Unit
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_calendar,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendar(view)
        initRecyclerView(view)
        initNestLayout(view)
    }

    private fun initCalendar(view : View){
        calendarWeekView = view.findViewById<CalendarWeekView>(R.id.calendar_week).apply {
            initOnSelectListener(object : OnSelectDateListener {

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

        monthView = view.findViewById<MonthView>(R.id.month_view).apply {
            initAdapter(onSelectDateListener = object : OnSelectDateListener{
                override fun onSelectDate(
                    calendarData: CalendarData,
                    row: Int,
                    col: Int,
                    type: CalendarAttr.CalendarType,
                    state: State,
                ) {
                    viewModel.onSelectedDate(calendarData)
                    if(this@CalendarFragment::dateListener.isInitialized){
                        dateListener(calendarData)
                    }
                }

                override fun onSelectOtherMonth(offset: Int) {

                }

            }, onSelectedDateHide = {
                calendarWeekView.isVisible = it
            }, calendarWeekView, CustomDayView(requireContext(), R.layout.custiom_day))
        }

        view.findViewById<TextView>(R.id.tv_today).apply {
            text = buildString {
                    append(getYear())
                    append("-")
                    append(getMonth())
                    append("-")
                    append(getDay())
                    append("")
                }
        }

    }

    private fun initRecyclerView(view : View){
        recyclerView = view.findViewById(R.id.rv_calendar)
        val adapter = ExpandableRecyclerView(requireContext()).apply {
            addObj(ItemTitle("aaaa"))
            addObj(ItemContext("1111"))
            addObj(ItemContext("2222"))
            addObj(ItemContext("3333"))
            addObj(ItemTitle("bbbb"))
            addObj(ItemContext("4444"))
            addObj(ItemContext("5555"))
            addObj(ItemTitle("cccc"))
            addObj(ItemContext("6666"))
            addObj(ItemContext("7777"))
            addObj(ItemContext("8888"))
        }
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        view.findViewById<Button>(R.id.btn_rv_test).setOnClickListener {
            if(adapter.isExpanded){
                adapter.closeList()
            }else{
                adapter.openList()
            }
        }
    }

    private fun initNestLayout(view: View){
        myNestedView = view.findViewById<MyNestedView?>(R.id.nsv_calendar).apply {
            initMonthView(monthView)
            initRecyclerView(recyclerView)
        }
    }

    fun backToday(){
        monthView.backToday()
    }

    fun setOnDateListener( listener : (CalendarData) -> Unit){
        dateListener = listener
    }

}