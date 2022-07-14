package com.yan.holidaytodo.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.yan.common.network.ApiResponse
import com.yan.common.base.BaseActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.extension
 * @ClassName:      ActivityExt
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 14:07:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    Activity的扩展函数进行网络请求
 */

fun <T> BaseActivity<ViewModel>.launchWithLoadingGetFlow(block: suspend () -> ApiResponse<T>): Flow<ApiResponse<T>> {
    return flow {
        emit(block())
    }.onStart {
        showLoading()
    }.onCompletion {
        dismissLoading()
    }
}

fun <T> BaseActivity<ViewModel>.launchWithLoadingAndCollect(block: suspend () -> ApiResponse<T>,
                                                 listenerBuilder: ResultBuilder<T>.() -> Unit) {
    lifecycleScope.launch {
        launchWithLoadingGetFlow(block).collect { response ->
            parseResultAndCallback(response, listenerBuilder)
        }
    }
}
