package com.yan.common.base

import androidx.lifecycle.Observer
import com.yan.common.BuildConfig
import com.yan.common.network.*

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo
 * @ClassName:      BaseRepository
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 14:07:00
 * @UpdateRemark:   更新说明： 更新网络请求仓库层
 * @Version:        1.1
 * @Description:    BaseRepository
 */
open class BaseRepository {
    suspend fun <T> executeHttp(block : suspend() -> ApiResponse<T>) : ApiResponse<T> {
        kotlin.runCatching {
            block.invoke()
        }.onSuccess {
            return handleHttpOk(it)
        }.onFailure {
            return handleHttpError(it)
        }
        return ApiEmptyResponse()
    }

    /**
     * 捕获到的异常
     */
    private fun <T> handleHttpError(e : Throwable) : ApiResponse<T>{
        if (BuildConfig.DEBUG) e.printStackTrace()

        return ApiErrorResponse(e)
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            getHttpSuccessResponse(data)
        } else {
            handlingApiExceptions(data.code, data.message)
            ApiFailedResponse(data.code, data.message)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(response: ApiResponse<T>): ApiResponse<T> {
        return if (response.data == null || response.data is List<*> && (response.data as List<*>).isEmpty()) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(response.data!!)
        }
    }

}
