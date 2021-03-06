package com.yan.common.network

import android.os.Looper
import android.util.Log
import com.google.gson.JsonParseException
import com.yan.common.extension.toast
import com.yan.holidaytodo.util.defaultSp
import com.yan.holidaytodo.util.put
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common.network
 * @ClassName:      HttpError
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 13:12:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    错误处理类
 */

//返回的不同类型抛出不同结果
enum class HttpError(var code: Int, var errorMsg: String) {
    //XXX_ERROR(code = xxx,errorMsg = "xxx")
    ACCOUNT_ERROR(-1001,"登录失效，请重新登录"),
    PARAMS_ERROR(4003, "params is error")
}
//处理网络请求返回的参数异常
fun handlingApiExceptions(code: Int?, errorMsg: String?) = when (code) {
    HttpError.ACCOUNT_ERROR.code -> {
        defaultSp.put {
            putString("Cookie",null)
        }
        Looper.prepare()
        toast(HttpError.ACCOUNT_ERROR.errorMsg)
        Looper.loop()
    }
    HttpError.PARAMS_ERROR.code -> {
        toast(HttpError.PARAMS_ERROR.errorMsg)
    }
    else -> errorMsg?.let { toast(it) }
}
//处理网络请求过程中抛出的异常
fun handlingExceptions(e: Throwable) = when (e) {
    is HttpException -> toast(e.message())

    is CancellationException -> {
    }

    is SocketTimeoutException -> {
    }

    is JsonParseException -> {
    }

    else -> {
    }
}