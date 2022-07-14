package com.yan.holidaytodo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.yan.holidaytodo.bean.CalendarAttr
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.bean.CalendarDrawer
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnAdapterSelectListener
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.util.getTouchSlop
import kotlin.math.abs

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.widget
 * @ClassName:      CalendarView
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 16:38:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    日期类
 */
class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(context,attrs, defStyleAttr, defStyleRes){

    private lateinit var onSelectListener: OnSelectDateListener
    private lateinit var calendarAttr: CalendarAttr

    fun initOnSelectListener(listener: OnSelectDateListener){
        onSelectListener = listener
    }

    fun initAttr(attr: CalendarAttr){
        calendarAttr = attr
    }

    fun initDayDrawer(dayDrawer: IDayDrawer) {
        drawer.dayDrawer = dayDrawer
    }

    companion object{
        const val TOTAL_ROW = 6
        const val TOTAl_COLUMN = 7
    }

    //单元格的高度
    private val cellHeight by lazy {
        val h = height / TOTAl_COLUMN
        calendarAttr.cellHeight = h
        h
    }
    //单元格的宽度
    private val cellWidth by lazy{
        val w = width / TOTAL_ROW
        calendarAttr.cellWidth = w
        w
    }

    //当前被选定的行数
    private var selectedRowIndex = 0

    //Adapter监听
    private lateinit var onAdapterSelectListener: OnAdapterSelectListener

    //滑动距离的常量
    private val touchSlop = getTouchSlop(context).toFloat()

    //日期
    val data : CalendarData
        get() = drawer.seedDate
    //周/月类型
    val type : CalendarAttr.CalendarType
        get() = calendarAttr.calendarType

    //日历的绘画类
    private val drawer by lazy {
        CalendarDrawer(context,this,calendarAttr).also{
            it.setOnSelectDataListener(onSelectListener)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //交给drawer画每一天
        drawer.drawDays(canvas)
    }

    private var posX = 0f
    private var posY = 0f

    /**
     * 确立点击位置的日期
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                posX = event.x
                posY = event.y
            }
            MotionEvent.ACTION_UP -> {
                val disX = event.x - posX
                val disY = event.y - posY
                if (abs(disX) < touchSlop && abs(disY) < touchSlop) {
                    val col: Int = (posX / cellWidth).toInt()
                    val row: Int = (posY / cellHeight).toInt()
                    onAdapterSelectListener.cancelSelectState()
                    drawer.onClickDate(col, row)
                    onAdapterSelectListener.updateSelectState()
                    invalidate()
                }
            }
        }
        return true
    }

    fun switchCalendarType(calendarType : CalendarAttr.CalendarType){
        calendarAttr.calendarType = calendarType
        drawer.setAttr(calendarAttr)
    }

    fun setSelectedRowIndex(rowIndex : Int){
        selectedRowIndex = rowIndex
    }

    fun resetSelectedRowIndex() {
        drawer.resetSelectedRowIndex()
    }

    fun setOnAdapterSelectListener(adapterSelectListener: OnAdapterSelectListener) {
        onAdapterSelectListener = adapterSelectListener
    }

    fun updateWeek(rowCount: Int) {
        drawer.updateWeek(rowCount)
        invalidate()
    }

    fun update(){
        drawer.update()
    }

    fun cancelSelectState() {
        drawer.cancelSelectState()
    }

    fun showDate(calendarData: CalendarData){
        drawer.showDate(calendarData)
    }

    fun getFirstDate(): CalendarData {
        return drawer.getFirstDate()
    }

    fun getLastDate(): CalendarData {
        return drawer.getLastDate()
    }

    fun getSelectedRowIndex() : Int{
        return selectedRowIndex
    }

}