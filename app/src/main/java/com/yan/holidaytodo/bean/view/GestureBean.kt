package com.yan.holidaytodo.bean.view

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.view
 * @ClassName:      GestureBean
 * @Author:         Yan
 * @CreateDate:     2022年07月20日 19:14:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    模拟手势滑动
 */

class GestureBean(
    /**
     * 起始位置 X
     */
    var startX: Float,
    /**
     * 起始位置 Y
     */
    var startY: Float,
    /**
     * 终点位置 X
     */
    var endX: Float,
    /**
     * 终点位置 Y
     */
    var endY: Float,
    duration: Long, speed: Int,
) {
    /**
     * 每个周期 x 移动的位置
     */
    var ratioX: Float

    /**
     * 每个周期 y 移动的位置
     */
    var ratioY: Float

    /**
     * 总共周期
     */
    var totalCount: Long = duration/speed

    /**
     * 当前周期
     */
    var count: Long = 0
    var period: Int = speed

    init {
        ratioX = (endX - startX) / totalCount
        ratioY = (endY - startY) / totalCount
    }
}
