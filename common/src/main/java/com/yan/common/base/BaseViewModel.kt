package com.yan.common.base

import android.content.Context
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yan.common.App
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common.base
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

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        mDisposableList
            .filter { !it.isDisposed }
            .forEach { it.dispose() }
        mDisposableList.clear()
    }

    /**
     * 开启协程并收集 Flow
     */
    protected fun <T> Flow<T>.collectLaunch(action: suspend (value: T) -> Unit) {
        viewModelScope.launch {
            collect{ action.invoke(it) }
        }
    }

}