package com.yan.common.network

import java.io.Serializable

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common.network
 * @ClassName:      ApiResponse
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 13:10:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    网络请求封装好返回
 */
open class ApiResponse<out T>(
    open val data: T? = null,
    open val errorCode: Int? = null,
    open val message: String? = null,
    open val error: Throwable? = null,
) : Serializable {
    val isSuccess: Boolean
        get() = errorCode == 0 || errorCode == 200
}


//数据为空
class ApiEmptyResponse<T> : ApiResponse<T>()
//数据返回成功
data class ApiSuccessResponse<T>(val response: T) : ApiResponse<T>(data = response)
//数据返回失败
data class ApiFailedResponse<T>(override val errorCode: Int?, override val message: String?) : ApiResponse<T>(errorCode = errorCode, message = message)
//抛出异常
data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>(error = throwable)
