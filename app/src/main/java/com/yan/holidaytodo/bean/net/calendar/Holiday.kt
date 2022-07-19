package com.yan.holidaytodo.bean.net.calendar

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.net
 * @ClassName:      Holiday
 * @Author:         Yan
 * @CreateDate:     2022年07月18日 23:28:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    处理Holiday属性不一样的方法，只能把全部属性都放在Holiday中
 */
data class Holiday(
    val after: Boolean,
    val holiday: Boolean,
    val name: String,
    val target: String,
    val wage: Int,
    val hashMap : HashMap<String, HolidayInner>
){
    data class HolidayInner(
        val after: Boolean,
        val holiday: Boolean,
        val name: String,
        val target: String,
        val wage: Int
    )
}