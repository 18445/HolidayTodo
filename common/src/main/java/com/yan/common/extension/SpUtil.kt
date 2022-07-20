package com.yan.holidaytodo.util

import android.content.Context
import android.content.SharedPreferences
import com.yan.common.App

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.util
 * @ClassName:      SharePreferences
 * @Author:         Yan
 * @CreateDate:     2022年07月21日 02:14:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    SpUtil
 */


/**
 * 快捷得到方式
 */
fun Context.getSp(name: String): SharedPreferences {
    return getSharedPreferences(name, Context.MODE_PRIVATE)
}

fun SharedPreferences.put(block : SharedPreferences.Editor.()->Unit){
    val edit = edit()
    edit.block()
    edit.apply()
}



/**
 * 全局通用的 Sp，用于整个应用内传递数据，重要数据以及大量数据请不要使用该 SP 保存
 *
 * 请在下面写上传递的 key 值，以 SP_模块名_作用名 开头命名，后面还可以细分
 *
 * 注意：这个是给整个应用全局使用的！
 */
val defaultSp
: SharedPreferences
    get() = App.appContext.getSharedPreferences("defaultSp", Context.MODE_PRIVATE)

const val SP_DEFAULT = "SharePreferences"