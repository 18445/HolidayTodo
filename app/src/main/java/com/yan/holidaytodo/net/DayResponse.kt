package com.yan.holidaytodo.net

import com.yan.holidaytodo.bean.Holiday
import com.yan.holidaytodo.bean.Type
import com.yan.holidaytodo.bean.Workday
import java.io.Serializable

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.net
 * @ClassName:      DayResponse
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 14:26:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    返回日期的Response
 *                  因为他返回的结果和一般返回的接口不统一
 *                  没有data字段，导致很难和common层的response统一
 *                  只好再写一个来统一这种情况
 */

open class DayResponse<out T>(
    open val code: Int? = null,
    open val message: String? = null,
    open val holiday: Holiday? = null,
    open val workday: Workday? = null,
    open val type: Type? = null,
) : Serializable {
    val isSuccess: Boolean
        get() = code == 0 || code == 200
}