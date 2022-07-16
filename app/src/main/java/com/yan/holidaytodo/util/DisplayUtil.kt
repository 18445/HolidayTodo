package com.yan.holidaytodo.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import kotlin.math.roundToInt

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.util
 * @ClassName:      DisplayUtil
 * @Author:         Yan
 * @CreateDate:     2022年07月16日 16:03:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    获得屏幕信息
 */
/**
 * 获取屏幕宽度
 * @return
 */
fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}

/**
 * 获取屏幕高度
 * @return
 */
fun getScreenHeight(): Int {
    return Resources.getSystem().displayMetrics.heightPixels
}

/**
 * 获取屏幕物理像素密度
 * @return
 */
fun getScreenDensity(): Float {
    return Resources.getSystem().displayMetrics.density
}

/**
 * dp转px
 * @return
 */
fun Int.dpToPx(): Int {
    return (Resources.getSystem().displayMetrics.density * this + 0.5f).roundToInt()
}

/**
 * sp转px
 * @return
 */
fun Int.spToPx(): Int {
    return (Resources.getSystem().displayMetrics.scaledDensity * this + 0.5f).roundToInt()
}

/**
 * px转dp
 * @return
 */
fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density + 0.5f).roundToInt()
}

/**
 * px转sp
 * @return
 */
fun Int.pxToSp(): Int {
    return (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).roundToInt()
}

/**
 * 获取状态栏的高度
 * @return
 */
fun Context.getStatusHeight(): Int {
    val rect = Rect()
    (this as Activity).window.decorView.getWindowVisibleDisplayFrame(rect)
    return rect.top
}

/**
 * 获取屏幕参数
 * @return
 */
fun getScreenParams(): String {
    return getScreenWidth().toString() + "*" + getScreenHeight()
}