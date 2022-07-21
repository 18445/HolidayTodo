package com.yan.holidaytodo.base

import com.yan.common.BuildConfig
import com.yan.common.network.*
import com.yan.holidaytodo.net.*

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
    suspend fun <T> executeHttp(block : suspend() -> DayResponse<T>) : DayResponse<T> {
        kotlin.runCatching {
            block.invoke()
        }.onSuccess {
            return handleHttpOk(it)
        }.onFailure {
            return handleHttpError(it)
        }
        return EmptyResponse()
    }

    /**
     * 捕获到的异常
     */
    private fun <T> handleHttpError(e : Throwable) : DayResponse<T>{
        if (BuildConfig.DEBUG) e.printStackTrace()
        handlingExceptions(e)
        return ErrorResponse(e)
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: DayResponse<T>): DayResponse<T> {
        return if (data.isSuccess) {
            getHttpSuccessResponse(data)
        } else {
            handlingApiExceptions(data.code, data.message)
            FailedResponse(data.code, data.message)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpSuccessResponse(response: DayResponse<T>): DayResponse<T> {

        return if (response.holiday == null && response.type == null
            && response.workday == null ) {
            EmptyResponse()
        } else {
            SuccessResponse(response.holiday,response.holidayData,response.workday,response.type)
        }
    }



    suspend fun <T> executeTaskHttp(block : suspend()-> ApiResponse<T>): ApiResponse<T> {
        kotlin.runCatching {
            block.invoke()
        }.onSuccess {   data: ApiResponse<T> ->
            return handleTaskHttpOk(data)
        }.onFailure {
            return handleTaskHttpError(it)
        }
        return ApiEmptyResponse()
    }

    /**
     * 非后台返回错误，捕获到的异常
     */
    private fun <T> handleTaskHttpError(e: Throwable): ApiErrorResponse<T> {
        if (BuildConfig.DEBUG) e.printStackTrace()
        handlingExceptions(e)
        return ApiErrorResponse(e)
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleTaskHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            getHttpTaskSuccessResponse(data)
        } else {
            handlingApiExceptions(data.errorCode, data.errorMsg)
            ApiFailedResponse(data.errorCode, data.errorMsg)
        }
    }

    /**
     * 成功和数据为空的处理
     */
    private fun <T> getHttpTaskSuccessResponse(response: ApiResponse<T>): ApiResponse<T> {
        return if (response.data == null || response.data is List<*> && (response.data as List<*>).isEmpty()) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(response.data!!)
        }
    }

}
