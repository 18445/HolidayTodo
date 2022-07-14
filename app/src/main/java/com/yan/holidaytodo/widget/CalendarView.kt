package com.yan.holidaytodo.widget

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
    private val onSelectListener: OnSelectDateListener,
    private val attr: CalendarAttr,
) : View(context,attrs, defStyleAttr, defStyleRes){
/**
    //行数 即高度
    private val row = 7
    //列数 即宽度
    private var column = 6
    //年份
    private val year = 2022
    //日期
    private val day = 7
    // 获得每个月第一天是星期几
    //（日：1 一：2 二：3 三：4 四：5 五：6 六：7）
    val firstDayOfMonth = getFirstDayOfMonth(year,day)

    val columnWidth by lazy {
        width / column
    }

    val rowHeight by lazy {
        height / row
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    lateinit var mDays : HashMap<Int,SingleDay>

    fun initDays(){
        for(i in 0..row){
            for(j in 0..column){
                val pair = (i.toString()+j).toInt() to CalendarData(14,"十六",false,false)
                SingleDay(context).also {
                    mDays[(i.toString()+j).toInt()] = it
                }
            }
        }
    }

    fun drawDays(canvas: Canvas){
        for (i in 0..row) {
            drawEachWeek(canvas,i)
        }
    }

    //绘画每一行
    private fun drawEachWeek(canvas: Canvas,row : Int) {
        for(i in 0..column){
            drawEachDay(canvas,row,i)
        }
    }

    //绘画每一天
    private fun drawEachDay(canvas: Canvas,row:Int,column:Int){

    }
*/

    companion object{
        const val TOTAL_ROW = 6
        const val TOTAl_COLUMN = 7
    }

    //单元格的高度
    private val cellHeight by lazy {
        val h = height / TOTAl_COLUMN
        attr.cellHeight = h
        h
    }
    //单元格的宽度
    private val cellWidth by lazy{
        val w = width / TOTAL_ROW
        attr.cellWidth = w
        w
    }

    private var selectedRowIndex = 0

    private lateinit var onAdapterSelectListener: OnAdapterSelectListener

    private val touchSlop = getTouchSlop(context).toFloat()

    val data : CalendarData
        get() = drawer.seedDate

    val type : CalendarAttr.CalendarType
        get() = attr.calendarType

    private val drawer = CalendarDrawer(context,this,attr).also{
        it.setOnSelectDataListener(onSelectListener)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //交给drawer画days
        drawer.drawDays(canvas)
    }

    private var posX = 0f
    private var posY = 0f

    /**
     * 确立点击位置的日期
     */
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
        attr.calendarType = calendarType
        drawer.setAttr(attr)
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

    fun setDayDrawer(dayDrawer: IDayDrawer) {
        drawer.dayDrawer = dayDrawer
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