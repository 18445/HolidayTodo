package com.yan.holidaytodo.repository

import com.yan.common.network.ApiGenerator
import com.yan.common.network.getHolidayBaseUrl
import com.yan.common.network.handlingExceptions
import com.yan.holidaytodo.base.BaseRepository
import com.yan.holidaytodo.bean.db.HolidayDB
import com.yan.holidaytodo.bean.net.calendar.*
import com.yan.holidaytodo.db.AppDatabase
import com.yan.holidaytodo.net.*
import com.yan.holidaytodo.util.judgeHoliday

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.repository
 * @ClassName:      HomeRepository
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 15:07:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    (再次吐槽这个接口，返回的数据既不规范，又不统一，同一字段下类居然也会不同，很是恼火)
 */
object HomeRepository : BaseRepository() {

    private val mCalendarService by lazy {
        CalendarService.Instance
    }

    /**
     * 获得日期信息
     * 使用HolidayData信息
     */
    suspend fun getDayInfo(date : String) : DayResponse<DayInfo>{
        val localDate = AppDatabase.INSTANCE.holidayDao().getHolidayByDate(date)
        if(localDate != null){
            return SuccessResponse(holidayData = HolidayData(localDate.date,localDate.holiday,localDate.name)
                ,holiday = null, workday = null, type = null)
        }
        val dayResponse =  executeHttp {
            mCalendarService.getDayInfo(date)
        }
        if (dayResponse is SuccessResponse){
            val dates = date.split("-")
            AppDatabase.INSTANCE.holidayDao().insertAll(HolidayDB(date,dayResponse.type!!.name,dates[0].toInt(),dates[1].toInt(),dates[2].toInt(),
                judgeHoliday(dayResponse.type!!.name)))
        }
        return dayResponse

    }

    /**
     * 获得节日信息
     * 不和其他统一是因为接口问题
     * 很难统一进行处理
     * 使用HolidayData信息
     */
    suspend fun getHolidayNext(date : String) : DayResponse<HolidayNext>{

        val localDate = AppDatabase.INSTANCE.holidayDao().getHolidayByDate(date)
        if(localDate != null){
            return SuccessResponse(holidayData = HolidayData(localDate.date,localDate.holiday,localDate.name)
                ,holiday = null, workday = null, type = null)
        }

        kotlin.runCatching {
            mCalendarService.getHolidayNext(date)
        }.onSuccess {
            val dates = date.split("-")
            AppDatabase.INSTANCE.holidayDao().insertAll(HolidayDB(it.holiday.date,it.holiday.name,dates[0].toInt(),dates[1].toInt(),dates[2].toInt(),
                it.holiday.holiday))
            return SuccessResponse(null,
                HolidayData(it.holiday.date,it.holiday.holiday,it.holiday.name),null,null)
        }.onFailure {
            handlingExceptions(it)
            return ErrorResponse(it)
        }
        return EmptyResponse()
    }

    /**
     * 获得工作日信息
     * 使用WorkdayNext
     */
    suspend fun getWorkdayNext(date : String) : DayResponse<WorkdayNext>{
        val localDate = AppDatabase.INSTANCE.holidayDao().getHolidayByDate(date)
        if(localDate != null){
            return SuccessResponse(holidayData = HolidayData(localDate.date,localDate.holiday,localDate.name)
                ,holiday = null, workday = HolidayNext.Workday(after = false,localDate.date,false,localDate.name,0,"",0), type = null)
        }

        val workdayNext = executeHttp {
            mCalendarService.getWorkDayNext(date)
        }

        if (workdayNext is SuccessResponse){
            val dates = date.split("-")
            AppDatabase.INSTANCE.holidayDao().insertAll(HolidayDB(date,workdayNext.workday!!.name,dates[0].toInt(),dates[1].toInt(),dates[2].toInt(),
                judgeHoliday(workdayNext.workday!!.name)))
        }
        return workdayNext
    }

    /**
     * 获得当前年份信息
     */
    suspend fun getYearInfo(date : String) : DayResponse<YearInfo>{

        val localDates = AppDatabase.INSTANCE.holidayDao().getHolidayByYear(date,true)
        if(localDates != null && localDates.size > 7 ){
            val hashMap : HashMap<String, HolidayData> = HashMap()
            for(i in localDates.indices){
                val day = localDates[i]
                val dayString = day.date
                hashMap[dayString] = HolidayData(day.date,day.holiday,day.name)
            }
            return SuccessResponse(holidayData = null
                ,holiday = hashMap, workday = null, type = null)
        }

        val yearInfo =  executeHttp {
            mCalendarService.getYearInfo(date)
        }

        if(yearInfo is SuccessResponse){
            val list = mutableListOf<HolidayDB>()
            for(day in yearInfo.holiday!!){
                val dates = day.value.date.split("-")
                list.add(HolidayDB(day.value.date,day.value.name,dates[0].toInt(),dates[1].toInt(),dates[2].toInt(),day.value.holiday))
            }
            for(holiday in list){
                AppDatabase.INSTANCE.holidayDao().insertAll(holiday)
            }
        }
        return yearInfo
    }

}