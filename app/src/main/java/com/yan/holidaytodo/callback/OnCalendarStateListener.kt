package com.yan.holidaytodo.callback

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.callback
 * @ClassName:      OnCalendarStateListener
 * @Author:         Yan
 * @CreateDate:     2022年07月16日 22:28:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    日历状态更改回调
 */
interface OnCalendarStateListener {

    fun onFoldingState()

    fun onStretchingState()

    fun onNormalState()
}