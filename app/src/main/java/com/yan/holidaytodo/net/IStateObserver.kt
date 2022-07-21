package com.yan.holidaytodo.net

import androidx.lifecycle.Observer
import com.yan.common.network.*

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.net
 * @ClassName:      IStateObserver
 * @Author:         Yan
 * @CreateDate:     2022年07月18日 20:10:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    同样是解决Common层无法统一网络请求后的数据
 */
abstract class IStateObserver <T> : Observer<DayResponse<T>> {
    override fun onChanged(apiResponse: DayResponse<T>?) {
        when (apiResponse) {
            is SuccessResponse -> onSuccess(apiResponse)
            is EmptyResponse -> onDataEmpty()
            is FailedResponse -> onFailed(apiResponse.code, apiResponse.message)
            is ErrorResponse -> onError(apiResponse.throwable)
        }
        onComplete()
    }

    abstract fun onSuccess(data : DayResponse<T>)

    abstract fun onDataEmpty()

    abstract fun onError(e: Throwable)

    abstract fun onFailed(errorCode: Int?, errorMsg: String?)

    abstract fun onComplete()
}

abstract class IStateTaskObserver <T> : Observer<ApiResponse<T>> {
    override fun onChanged(apiResponse: ApiResponse<T>?) {
        when (apiResponse) {
            is ApiSuccessResponse -> onSuccess(apiResponse.response)
            is ApiEmptyResponse -> onDataEmpty()
            is ApiFailedResponse -> onFailed(apiResponse.errorCode, apiResponse.errorMsg)
            is ApiErrorResponse -> onError(apiResponse.throwable)
        }
        onComplete()
    }

    abstract fun onSuccess(data: T)

    abstract fun onDataEmpty()

    abstract fun onError(e: Throwable)

    abstract fun onFailed(errorCode: Int?, errorMsg: String?)

    abstract fun onComplete()
}