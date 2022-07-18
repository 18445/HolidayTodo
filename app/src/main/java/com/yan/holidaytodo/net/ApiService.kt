package com.yan.holidaytodo.net

import com.yan.common.network.ApiGenerator
import retrofit2.http.GET
import retrofit2.http.Path


/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.net
 * @ClassName:      ApiService
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 13:39:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    日历Api
 */
interface ApiService {

    companion object {
        val Instance by lazy {
            ApiGenerator.getApiService(ApiService::class)
        }
    }

    /**
    {
    "code": 0,              // 0服务正常。-1服务出错
    "type": {
    "type": enum(0, 1, 2, 3), // 节假日类型，分别表示 工作日、周末、节日、调休。
    "name": "周六",         // 节假日类型中文名，可能值为 周一 至 周日、假期的名字、某某调休。
    "week": enum(1 - 7)    // 一周中的第几天。值为 1 - 7，分别表示 周一 至 周日。
    },
    "holiday": {
    "holiday": false,     // true表示是节假日，false表示是调休
    "name": "国庆前调休",  // 节假日的中文名。如果是调休，则是调休的中文名，例如'国庆前调休'
    "wage": 1,            // 薪资倍数，1表示是1倍工资
    "after": false,       // 只在调休下有该字段。true表示放完假后调休，false表示先调休再放假
    "target": '国庆节'     // 只在调休下有该字段。表示调休的节假日
    }
    }
     */
//    @GET("/api/holiday/info/{data}")
//    suspend fun getDayInfo(
//        @Path("data") data: String,
//    ): DayResponse<Holiday>


}