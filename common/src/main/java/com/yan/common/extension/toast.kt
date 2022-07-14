package com.yan.common.extension

import android.widget.Toast
import com.yan.common.App

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common.extension
 * @ClassName:      toast
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 15:13:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    Toast的扩展函数
 */

fun toast(s: CharSequence) {
    Toast.makeText(App.appContext, s, Toast.LENGTH_SHORT).show()
}

fun toastLong(s: CharSequence) {
    Toast.makeText(App.appContext, s, Toast.LENGTH_LONG).show()
}

fun String.toast() = toast(this)
fun String.toastLong() = toastLong(this)