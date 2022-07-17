package com.yan.holidaytodo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import com.yan.holidaytodo.bean.CalendarAttr
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.helper.CalendarWeekDrawer
import com.yan.holidaytodo.util.getRowIndexInMonth
import com.yan.holidaytodo.util.getTheWholeMonth
import kotlin.reflect.jvm.internal.impl.incremental.components.Position

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      CalendarWeekView
 * @Author:         Yan
 * @CreateDate:     2022年07月17日 17:22:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    周日历
 */
class CalendarWeekView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    //当前应该所属的界面
    companion object{
        var shouldBeShownPosition = MonthView.CURRENT_DAY_INDEX
    }
    //当前被选定的行数
    private var selectedRowIndex = 0

    //日历切换监听
    private lateinit var onSelectListener: OnSelectDateListener

    //日历的绘画类
    private lateinit var drawer: CalendarWeekDrawer

    private fun initDrawer(context: Context) {
        drawer = CalendarWeekDrawer(context, this).also {
            it.initSeedData(shouldBeShownPosition)
            it.setOnSelectDataListener(onSelectListener)
        }
        drawer.selectedRowIndex = (getRowIndexInMonth(data, getTheWholeMonth(data)))
    }

    fun initOnSelectListener(listener: OnSelectDateListener) {
        onSelectListener = listener
    }

    fun initDayDrawer(dayDrawer: IDayDrawer) {
        initDrawer(context)
        drawer.dayDrawer = dayDrawer
        invalidate()
    }

    //当前日期
    val data: CalendarData
        get() = drawer.seedDate

    //周/月类型
    private lateinit var beSelectedData : CalendarData

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //交给drawer画一周的每一天
        if ( this::onSelectListener.isInitialized
        ) {
            drawer.drawDaysOfWeek(canvas)
        }

    }

    //用于记录总滑动位置
    private var posX = 0f
    private var posY = 0f

    /**
     * 确立点击位置的日期
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        //同级View只有一个处理事件？

//        when (event.action) {
//
//            MotionEvent.ACTION_DOWN -> {
//                posX = event.x
//                posY = event.y
//            }
//
//            MotionEvent.ACTION_MOVE -> {
//                val totalOffsetY = event.y - posY
//
//
//                Log.e("hasMoved:",totalOffsetY.toString())
//                Log.e("hasMoved:",cellHeight.toString())
//
//                if(abs(totalOffsetY) > cellHeight / 10 ){
//                    return false
//                }
//            }
//
//            MotionEvent.ACTION_UP -> {
//                val disX = event.x - posX
//                val disY = event.y - posY
//
//                if (abs(disX) < touchSlop && abs(disY) < touchSlop) {//点击事件
//                    val col: Int = (posX / cellWidth + 0.5).toInt()
//                    val row: Int = (posY / currentCellHeight).toInt()
//                    cancelSelectState()
//                    drawer.onClickDate(col, row)
//                    update()
//                    invalidate()
//                }
//
//            }
//        }

        return false
    }

    fun setSelectedRowIndex(rowIndex: Int) {
        this.isVisible = false
        selectedRowIndex = rowIndex
        updateWeek(rowIndex)
    }

    fun onClickItem(calendarData: CalendarData,row : Int,col : Int){
        setSelectedRowIndex(row)
        drawer.selectedRowIndex = row
        drawer.onClickDate(calendarData,row,col)
    }

    fun resetSelectedRowIndex() {
        drawer.resetSelectedRowIndex()
    }

    private fun updateWeek(rowCount: Int) {
        drawer.updateWeek(rowCount)
        invalidate()
    }

    private fun cancelSelectState() {
        drawer.cancelSelectState()
    }

    fun changeSelectedData(calendarData: CalendarData){
        beSelectedData = calendarData
    }

    fun changePosition(position: Int,beClick : Boolean){

        shouldBeShownPosition = position
        drawer.changePosition(position)

        if(!beClick){//不是被点击改变
            selectedRowIndex = 0
            drawer.selectedRowIndex = 0
        }else{
            selectedRowIndex = drawer.changeSelectedData(beSelectedData)
        }
    }

}