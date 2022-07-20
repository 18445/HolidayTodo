package com.yan.holidaytodo.bean

import com.yan.holidaytodo.util.defaultSp

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean
 * @ClassName:      Session
 * @Author:         Yan
 * @CreateDate:     2022年07月21日 02:07:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    此类用来判断是登录状态
 */
object Session {

    //cookie信息
    val cookie : String ?
        get() {
            return defaultSp.getString("Cookie",null)
        }

    //判断是否登录
    val state : Boolean
        get() {
            return cookie != null
        }


}