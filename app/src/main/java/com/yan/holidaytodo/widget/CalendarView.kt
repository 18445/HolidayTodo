package com.yan.holidaytodo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.yan.holidaytodo.bean.CalendarAttr
import com.yan.holidaytodo.bean.CalendarData
import com.yan.holidaytodo.helper.CalendarDrawer
import com.yan.holidaytodo.callback.IDayDrawer
import com.yan.holidaytodo.callback.OnAdapterSelectListener
import com.yan.holidaytodo.callback.OnSelectDateListener
import com.yan.holidaytodo.helper.CalendarMover
import com.yan.holidaytodo.util.calcOffset
import com.yan.holidaytodo.util.dpToPx
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

    //Adapter监听
    private lateinit var onAdapterSelectListener: OnAdapterSelectListener
    //外部监听
    private lateinit var onSelectListener: OnSelectDateListener
    //设置日历属性
    private lateinit var calendarAttr: CalendarAttr
    //设置日历所在页面
    private var currentPosition = -1
    //日历的绘画类
    private lateinit var drawer : CalendarDrawer

    private fun initDrawer(context:Context){
        drawer = CalendarDrawer(context,this,calendarAttr).also {
            it.initSeedData(currentPosition)
            it.setOnSelectDataListener(onSelectListener)
        }
    }

    fun initOnSelectListener(listener: OnSelectDateListener){
        onSelectListener = listener
        invalidate()
    }

    fun initAttr(attr: CalendarAttr){
        calendarAttr = attr
        invalidate()
    }

    fun initDayDrawer(dayDrawer: IDayDrawer) {
        initDrawer(context)
        drawer.dayDrawer = dayDrawer
        invalidate()
    }

    fun initAdapter(listener : OnAdapterSelectListener){
        onAdapterSelectListener = listener
        invalidate()
    }

    fun initCurrentPosition(position : Int){
        currentPosition = position
        invalidate()
    }

    companion object{
        const val TOTAL_ROW = 6
        const val TOTAl_COLUMN = 7
    }

    //单元格的初始高度
    private val cellHeight by lazy {
        val h = height / TOTAl_COLUMN
        calendarAttr.cellHeight = h
        h
    }
    //单元格的初始宽度
    private val cellWidth by lazy{
        val w = width / TOTAL_ROW
        calendarAttr.cellWidth = w
        w
    }

    //上下滑动阈值
    private val moveUpOrDown by lazy {
        cellHeight
    }

    //移动类
    private val calendarMover = CalendarMover(this)

    //单元格现在的高度
    private var currentCellHeight = -1

    //现在的整体高度
    var mCurrentHeight = -1
        private set
    //当前被选定的行数
    private var selectedRowIndex = 0

    //滑动距离的常量
    private val touchSlop = getTouchSlop(context).toFloat()

    //现在的view状态
    private var calendarState = CalendarMover.CalendarState.NORMAL

    //日期
    val data : CalendarData
        get() = drawer.seedDate

    //周/月类型
    val type : CalendarAttr.CalendarType
        get() = calendarAttr.calendarType

    //设置的最小高度 dp 单位
    val mLeastHeight = 60
    //设置的最大高度 dp 单位
    val mMostHeight = 650
    //初始长度 px 单位
    var mNormalHeight = -1
        private set

    //下拉比例
    var downPercent = 0f
        get() = calcOffset(field,-1f,1f)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if(mNormalHeight == -1){
            mNormalHeight = h
        }
        mCurrentHeight = h
        currentCellHeight = h / TOTAl_COLUMN
        Log.d("currentCellHeight",currentCellHeight.toString())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //交给drawer画每一天
        if(this::onAdapterSelectListener.isInitialized
            && this::onSelectListener.isInitialized
            && this::calendarAttr.isInitialized
            &&  currentPosition != -1){
                drawer.drawDays(canvas, downPercent)
        }
    }

    //用于记录总滑动位置
    private var posX = 0f
    private var posY = 0f

    //用于记录每次滑动位置
    private var moveY = 0f

    /**
     * 确立点击位置的日期
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                posX = event.x
                posY = event.y
                moveY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                //总偏移量
                val totalOffsetY =  event.y - posY
                //每次的偏移量
                val distanceY = event.y - moveY

                if(totalOffsetY < -moveUpOrDown && layoutParams.height >= mLeastHeight.dpToPx() ){//view 向上滑动 (折叠)
                    parent.requestDisallowInterceptTouchEvent(true)
                    calendarMover.calendarMove(calendarState,distanceY)
                }else if(totalOffsetY > moveUpOrDown && layoutParams.height <= mMostHeight.dpToPx()){//view 普通状态向下滑动 (展开)
                    parent.requestDisallowInterceptTouchEvent(true)
                    calendarMover.calendarMove(calendarState,distanceY)
                }
                moveY = event.y
            }


            MotionEvent.ACTION_UP -> {
                val disX = event.x - posX
                val disY = event.y - posY



                if (abs(disX) < touchSlop && abs(disY) < touchSlop) {//点击事件
                    val col: Int = (posX / cellWidth + 0.5).toInt()
                    val row: Int = (posY / currentCellHeight).toInt()
                    onAdapterSelectListener.cancelSelectState()
                    cancelSelectState()
                    drawer.onClickDate(col, row)
                    onAdapterSelectListener.updateSelectState()
                    update()
                    invalidate()
                }

                //普通状态向下滑动
                if (calendarState === CalendarMover.CalendarState.NORMAL && disY > (mMostHeight.dpToPx() - mNormalHeight) / 2
                    && mCurrentHeight >= mNormalHeight){ //普通状态拉伸
                    calendarMover.moveToDown()
                    calendarState = CalendarMover.CalendarState.STRETCHING
                }else if (calendarState === CalendarMover.CalendarState.NORMAL && disY > 0 && mCurrentHeight >= mNormalHeight){ //普通状态恢复
                    calendarMover.moveToNormal()
                }
                //普通状态向上滑动
                else if(calendarState === CalendarMover.CalendarState.NORMAL && -disY > (mNormalHeight - mLeastHeight.dpToPx())/2
                    && mCurrentHeight <= mNormalHeight) { //普通状态收缩
                    calendarMover.moveToFoldingTop()
                    calendarState = CalendarMover.CalendarState.FOLDING
                }else if(calendarState === CalendarMover.CalendarState.NORMAL && -disY > 0 && mCurrentHeight <= mNormalHeight){ //普通状态恢复
                    calendarMover.moveToNormalWhenUp()
                }
                //拉伸状态向上滑动
                else if (calendarState === CalendarMover.CalendarState.STRETCHING && -disY > (mMostHeight.dpToPx() - mNormalHeight) / 2){ //恢复为普通状态
                    calendarMover.moveToNormal()
                    calendarState = CalendarMover.CalendarState.NORMAL
                }else if (calendarState === CalendarMover.CalendarState.STRETCHING && -disY > 0){ //恢复为拉伸状态
                    calendarMover.moveToDown()
                }
                //折叠状态向下滑动
                else if(calendarState === CalendarMover.CalendarState.FOLDING && disY >= cellHeight * 2
                    && mCurrentHeight <= mNormalHeight){ //恢复为普通状态
                    calendarMover.moveToNormalWhenFolding()
                    calendarState = CalendarMover.CalendarState.NORMAL
                }else if(calendarState === CalendarMover.CalendarState.FOLDING && disY > 0 && mCurrentHeight <= mNormalHeight){
                    calendarMover.moveToFoldingTop()
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