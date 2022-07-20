package com.yan.holidaytodo.ui.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yan.holidaytodo.base.BaseViewModel
import com.yan.holidaytodo.bean.net.calendar.DayInfo
import com.yan.holidaytodo.bean.net.calendar.HolidayNext
import com.yan.holidaytodo.bean.net.calendar.WorkdayNext
import com.yan.holidaytodo.bean.net.calendar.YearInfo
import com.yan.holidaytodo.bean.view.CalendarData
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

     private val dayInfo = StateLiveData<DayInfo>()

    private val holidayNext = StateLiveData<HolidayNext>()

    private val workdayNext = StateLiveData<WorkdayNext>()

    private val yearInfo = StateLiveData<YearInfo>()

    private val _todayDate = MutableLiveData<String>()

    val todayDate : LiveData<String>
        get() = _todayDate

    fun getDayInfo(date : String){
        viewModelScopeLaunch{
            dayInfo.postValue(HomeRepository.getDayInfo(date))
        }
    }

    fun getHolidayNext(date : String){
        viewModelScopeLaunch {
            holidayNext.postValue(HomeRepository.getHolidayNext(date))
        }
    }

    fun getWorkdayNext(date : String){
        viewModelScopeLaunch {
            workdayNext.postValue(HomeRepository.getWorkdayNext(date))
        }
    }

    fun getYearInfo(date : String){
        viewModelScopeLaunch {
            yearInfo.postValue(HomeRepository.getYearInfo(date))
        }
    }

    fun onSelectedDate(calendarData: CalendarData){
        _todayDate.value = "${calendarData.year}-${calendarData.month}-${calendarData.day}"
    }


    fun observeDayInfo(owner: LifecycleOwner, listenerBuilder: StateLiveData<DayInfo>.ListenerBuilder.() -> Unit){
        observeStateLiveData(dayInfo,owner,listenerBuilder)
    }

    fun observeHolidayNext(owner : LifecycleOwner,listenerBuilder: StateLiveData<HolidayNext>.ListenerBuilder.() -> Unit){
        observeStateLiveData(holidayNext,owner,listenerBuilder)
    }

    fun observeWorkdayNext(owner: LifecycleOwner,listenerBuilder: StateLiveData<WorkdayNext>.ListenerBuilder.() -> Unit){
        observeStateLiveData(workdayNext,owner,listenerBuilder)
    }

    fun observeYearInfo(owner: LifecycleOwner,listenerBuilder: StateLiveData<YearInfo>.ListenerBuilder.() -> Unit){
        observeStateLiveData(yearInfo,owner,listenerBuilder)

    }

}