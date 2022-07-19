package com.yan.common.extension

import androidx.lifecycle.Observer
import com.yan.common.network.*

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common.extension
 * @ClassName:      IStateObserver
 * @Author:         Yan
 * @CreateDate:     2022年07月18日 17:10:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    网络请求返回的数据类型声明周期监听
 */

abstract class IStateObserver <T> : Observer<ApiResponse<T>> {
    override fun onChanged(apiResponse: ApiResponse<T>?) {
        when (apiResponse) {
            is ApiSuccessResponse -> onSuccess(apiResponse.response)
            is ApiEmptyResponse -> onDataEmpty()
            is ApiFailedResponse -> onFailed(apiResponse.errorCode  , apiResponse.message)
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