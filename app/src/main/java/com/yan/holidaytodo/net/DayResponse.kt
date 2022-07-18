package com.yan.holidaytodo.net


import com.google.gson.annotations.SerializedName
import com.yan.common.network.ApiResponse
import com.yan.holidaytodo.bean.net.*
import java.io.Serializable
import java.time.Year

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
    open val holiday: Map<String, HolidayData>? =null,
    open val holidayData : HolidayData? = null,
    open val type: DayInfo.Type? = null,
    open val workday: HolidayNext.Workday? = null,
    open val error: Throwable? = null,
) : Serializable {
    val isSuccess: Boolean
        get() = code == 0 || code == 200
}


//数据为空
class EmptyResponse<T> : DayResponse<T>()

//数据返回成功
data class SuccessResponse<T>(override val holiday: Map<String, HolidayData>?,
                              override val holidayData : HolidayData?,
                              override val workday: HolidayNext.Workday?,
                              override val type: DayInfo.Type?) : DayResponse<T>(holiday = holiday, workday = workday, type = type, holidayData = holidayData)

//数据返回失败
data class FailedResponse<T>(override val code: Int?, override val message: String?) : DayResponse<T>(code = code, message = message)

//抛出异常
data class ErrorResponse<T>(val throwable: Throwable) : DayResponse<T>(error = throwable)