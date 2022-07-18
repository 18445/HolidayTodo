package com.yan.holidaytodo.base

import android.app.Activity
import android.content.Context
import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yan.common.App
import com.yan.holidaytodo.bean.net.DayInfo
import com.yan.holidaytodo.net.StateLiveData
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.base
 * @ClassName:      BaseViewModel
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 15:05:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    BaseViewModel
 */

abstract class BaseViewModel : ViewModel() {

    val appContext: Context
        get() = App.appContext

    private val mDisposableList = mutableListOf<Disposable>()

    protected fun Disposable.lifecycle(): Disposable {
        mDisposableList.add(this)
        return this
    }

    fun viewModelScopeLaunch(block : suspend()-> Unit){
        viewModelScope.launch {
            block.invoke()
        }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        mDisposableList
            .filter { !it.isDisposed }
            .forEach { it.dispose() }
        mDisposableList.clear()
    }

    /**
     * 为了将ViewModel层的StateLiveData给UI层进行观察
     * 同时将StateLiveData不对外暴露
     * 保证MVVM的数据流通
     */
    private inline fun <T : StateLiveData<G>,G>
            BaseViewModel.observeStateLiveData(data : T, owner: LifecycleOwner, crossinline listenerBuilder: StateLiveData<G>.ListenerBuilder.() -> Unit)
    {
        data.observeState(owner){
            this.also(listenerBuilder)
        }
    }

}