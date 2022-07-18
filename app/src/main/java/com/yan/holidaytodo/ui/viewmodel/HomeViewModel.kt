package com.yan.holidaytodo.ui.viewmodel

import com.yan.holidaytodo.base.BaseViewModel
import com.yan.holidaytodo.bean.net.DayInfo
import com.yan.holidaytodo.bean.net.HolidayNext
import com.yan.holidaytodo.bean.net.WorkdayNext
import com.yan.holidaytodo.net.StateLiveData
import com.yan.holidaytodo.repository.HomeRepository

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.ui.viewmodel
 * @ClassName:      HomeViewModel
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 15:04:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    HomeViewModel
 */
class HomeViewModel : BaseViewModel() {

    val dayInfo = StateLiveData<DayInfo>()

    val holidayNext = StateLiveData<HolidayNext>()

    val workdayNext = StateLiveData<WorkdayNext>()

    fun getDayInfo(date : String){
        viewModelScopeLaunch {
            dayInfo.value = HomeRepository.getDayInfo(date)
        }
    }

    fun getHolidayNext(date : String){
        viewModelScopeLaunch {
            holidayNext.value = HomeRepository.getHolidayNext(date)
        }
    }

    fun getWorkdayNext(date : String){
        viewModelScopeLaunch {
            workdayNext.value = HomeRepository.getWorkdayNext(date)
        }
    }

}