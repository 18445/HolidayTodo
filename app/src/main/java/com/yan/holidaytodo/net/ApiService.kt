package com.yan.holidaytodo.net

import com.yan.common.network.ApiGenerator
import com.yan.holidaytodo.bean.net.DayInfo
import com.yan.holidaytodo.bean.net.HolidayNext
import com.yan.holidaytodo.bean.net.WorkdayNext
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
     * 获得指定节假日的信息
     * 接口地址：http://timor.tech/api/holiday/info/$date
     * @params $date: 指定日期的字符串，格式 ‘
     * 2018-02-23’。可以省略，则默认服务器的当前时间。
     * @return json: 如果不是节假日，holiday字段为null。
     */
    @GET("/api/holiday/info/{date}")
    suspend fun getDayInfo(
        @Path("date") date: String,
    ) : DayResponse<DayInfo>

    /**
     * 获取指定日期的下一个节假日（如果在放假前有调休，也会返回）
     * 接口地址: http://timor.tech/api/holiday/next/$date?type=Y&week=Y
     * @params $date: 指定日期的字符串，格式 ‘2018-02-23’。可以省略，则默认服务器的当前时间。
     * @params type: 是否返回日期类型，默认不返回。可选值：’Y’ 返回，’N’ 不返回。
     * @params week: 节假日是否包含周末，默认不包含。可选值：’Y’ 包含周末，’N’ 不包含。
     * @return json: 返回指定日期的下一个最近的节假日，如果在放假之前要调休，则会一起返回调休的信息。如果包含周末，则节日和周末冲突时，以节日为优先级。 比如特殊情
     */
    @GET("/api/holiday/next/{date}")
    suspend fun getHolidayNext(
        @Path("date") date : String,
    ) : DayResponse<HolidayNext>

    /**
     * 获取指定日期的下一个工作日（工作日包含正常工作日、调休）不包含当天
     * 接口地址: http://timor.tech/api/holiday/workday/next/$date
     *
     * @params $data: 指定日期的字符串，格式 ‘2020-01-20’。可以省略，则默认服务器的当前时间。
     * @return json: 返回指定日期的下一个最近的工作日。工作日包含正常工作日、调休，不包含当天。
     */
    @GET("/api/holiday/workday/next/{date}")
    suspend fun getWorkDayNext(
        @Path("date") date : String
    ) : DayResponse<WorkdayNext>

}