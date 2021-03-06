package com.yan.holidaytodo.net

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yan.common.extension.toast
import com.yan.common.network.ApiResponse

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.net
 * @ClassName:      StateLiveDate
 * @Author:         Yan
 * @CreateDate:     2022年07月18日 20:08:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    也是为了解决common层无法统一字段的原因（返回json格式太奇怪了）
 */


class StateMutableLiveData<T> : StateLiveData<T>(){
    public override fun setValue(value: DayResponse<T>?) {
        super.setValue(value)
    }

    public override fun postValue(value: DayResponse<T>?) {
        super.postValue(value)
    }
}


open class StateLiveData<T> : LiveData<DayResponse<T>>() {

    fun observeState(owner: LifecycleOwner, listenerBuilder: ListenerBuilder.() -> Unit) {
        val listener = ListenerBuilder().also(listenerBuilder)
        val value = object : IStateObserver<T>() {

            override fun onSuccess(data: DayResponse<T>) {
                listener.mSuccessListenerAction?.invoke(data)
            }

            override fun onError(e: Throwable) {
                listener.mErrorListenerAction?.invoke(e) ?: toast("网络似乎出现了异常了呢")
            }

            override fun onDataEmpty() {
                listener.mEmptyListenerAction?.invoke()
            }

            override fun onComplete() {
                listener.mCompleteListenerAction?.invoke()
            }

            override fun onFailed(errorCode: Int?, errorMsg: String?) {
                listener.mFailedListenerAction?.invoke(errorCode, errorMsg)
            }


        }
        super.observe(owner, value)
    }

    inner class ListenerBuilder {
        internal var mSuccessListenerAction: ((DayResponse<T>) -> Unit)? = null
        internal var mFailedListenerAction: ((Int?, String?) -> Unit)? = null
        internal var mErrorListenerAction: ((Throwable) -> Unit)? = null
        internal var mEmptyListenerAction: (() -> Unit)? = null
        internal var mCompleteListenerAction: (() -> Unit)? = null

        fun onSuccess(action: ((DayResponse<T>)) -> Unit) {
            mSuccessListenerAction = action
        }

        fun onFailed(action: (Int?, String?) -> Unit) {
            mFailedListenerAction = action
        }

        fun onException(action: (Throwable) -> Unit) {
            mErrorListenerAction = action
        }

        fun onEmpty(action: () -> Unit) {
            mEmptyListenerAction = action
        }

        fun onComplete(action: () -> Unit) {
            mCompleteListenerAction = action
        }
    }

}

class StateTaskMutableLiveData<T> : StateTaskLiveData<T>(){

    public override fun setValue(value: ApiResponse<T>?) {
        super.setValue(value)
    }

    public override fun postValue(value: ApiResponse<T>?) {
        super.postValue(value)
    }

}

open class StateTaskLiveData<T> : LiveData<ApiResponse<T>>() {

    fun observeState(owner: LifecycleOwner, listenerBuilder: ListenerBuilder.() -> Unit) {
        val listener = ListenerBuilder().also(listenerBuilder)
        val value = object : IStateTaskObserver<T>() {

            override fun onSuccess(data: T) {
                listener.mSuccessListenerAction?.invoke(data)
            }

            override fun onError(e: Throwable) {
                listener.mErrorListenerAction?.invoke(e) ?: toast("Http Error")
            }

            override fun onDataEmpty() {
                listener.mEmptyListenerAction?.invoke()
            }

            override fun onComplete() {
                listener.mCompleteListenerAction?.invoke()
            }

            override fun onFailed(errorCode: Int?, errorMsg: String?) {
                listener.mFailedListenerAction?.invoke(errorCode, errorMsg)
            }

        }
        super.observe(owner, value)
    }

    inner class ListenerBuilder {
        internal var mSuccessListenerAction: ((T) -> Unit)? = null
        internal var mErrorListenerAction: ((Throwable) -> Unit)? = null
        internal var mEmptyListenerAction: (() -> Unit)? = null
        internal var mCompleteListenerAction: (() -> Unit)? = null
        internal var mFailedListenerAction: ((Int?, String?) -> Unit)? = null

        fun onSuccess(action: (T) -> Unit) {
            mSuccessListenerAction = action
        }

        fun onFailed(action: (errCode : Int?, errMsg : String?) -> Unit) {
            mFailedListenerAction = action
        }

        fun onException(action: (Throwable) -> Unit) {
            mErrorListenerAction = action
        }

        fun onEmpty(action: () -> Unit) {
            mEmptyListenerAction = action
        }

        fun onComplete(action: () -> Unit) {
            mCompleteListenerAction = action
        }
    }

}

