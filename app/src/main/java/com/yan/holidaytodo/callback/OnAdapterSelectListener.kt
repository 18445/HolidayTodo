package com.yan.holidaytodo.callback

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.callback
 * @ClassName:      OnAdapterSelectListener
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 19:32:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:
 */

interface OnAdapterSelectListener {
    fun cancelSelectState()
    fun updateSelectState()
}