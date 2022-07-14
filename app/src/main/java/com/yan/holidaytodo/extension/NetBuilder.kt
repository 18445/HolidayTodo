package com.yan.holidaytodo.extension

import com.yan.common.network.*

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.extension
 * @ClassName:      NetBuilder
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 14:12:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    网络请求结果
 */

class ResultBuilder<T> {
    var onSuccess: (data: T?) -> Unit = {}
    var onDataEmpty: () -> Unit = {}
    var onFailed: (errorCode: Int?, errorMsg: String?) -> Unit = { _, _ -> }
    var onError: (e: Throwable) -> Unit = {}
    var onComplete: () -> Unit = {}
}

fun <T> parseResultAndCallback(response: ApiResponse<T>,
                                       listenerBuilder: ResultBuilder<T>.() -> Unit) {
    val listener = ResultBuilder<T>().also(listenerBuilder)
    when (response) {
        is ApiSuccessResponse -> listener.onSuccess(response.response)
        is ApiEmptyResponse -> listener.onDataEmpty()
        is ApiFailedResponse -> listener.onFailed(response.code, response.message)
        is ApiErrorResponse -> listener.onError(response.throwable)
    }
    listener.onComplete()
}
