package com.yan.holidaytodo.util

import com.google.android.material.textfield.TextInputLayout

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.util
 * @ClassName:      TextUtil
 * @Author:         Yan
 * @CreateDate:     2022年07月21日 19:58:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    TextInputLayout工具
 */


/**
 * 验证非空
 * @param text
 * @return
 */
fun TextInputLayout.validateText(text :String ,emptyMsg : String): Boolean {
    if (text.isEmpty()){
        this.isErrorEnabled = true
        showError(this,emptyMsg)
        return false
    }
    this.isErrorEnabled = false
    return true
}

/**
 * 显示错误提示，并获取焦点
 * @param textInputLayout
 * @param error
 */
private fun showError(textInputLayout: TextInputLayout, error: String) {
    textInputLayout.error = error
    textInputLayout.editText!!.isFocusable = true
    textInputLayout.editText!!.isFocusableInTouchMode = true
    textInputLayout.editText!!.requestFocus()
}