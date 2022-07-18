package com.yan.holidaytodo.repository

import com.yan.common.network.ApiGenerator
import com.yan.holidaytodo.base.BaseRepository
import com.yan.holidaytodo.bean.net.DayInfo
import com.yan.holidaytodo.bean.net.HolidayNext
import com.yan.holidaytodo.bean.net.WorkdayNext
import com.yan.holidaytodo.net.ApiService
import com.yan.holidaytodo.net.DayResponse

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.repository
 * @ClassName:      HomeRepository
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 15:07:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:
 */
object HomeRepository : BaseRepository() {

    private val mApiService = ApiGenerator.getApiService(ApiService::class)

    /**
     * 获得日期信息
     */
    suspend fun getDayInfo(date : String) : DayResponse<DayInfo>{
        return executeHttp {
            mApiService.getDayInfo(date)
        }
    }

    /**
     * 获得节日信息
     */
    suspend fun getHolidayNext(date : String) : DayResponse<HolidayNext>{
        return executeHttp {
            mApiService.getHolidayNext(date)
        }
    }

    /**
     * 获得工作日信息
     */
    suspend fun getWorkdayNext(date: String) : DayResponse<WorkdayNext>{
        return executeHttp {
            mApiService.getWorkDayNext(date)
        }
    }


}