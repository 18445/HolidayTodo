package com.yan.holidaytodo.ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.yan.common.extension.toast
import com.yan.holidaytodo.R
import com.yan.holidaytodo.bean.view.CalendarData
import com.yan.holidaytodo.ui.viewmodel.TaskViewModel
import com.yan.holidaytodo.util.getDay
import com.yan.holidaytodo.util.getMonth
import com.yan.holidaytodo.util.getYear

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.ui.fragment
 * @ClassName:      TaskBottomSheetDialog
 * @Author:         Yan
 * @CreateDate:     2022年07月21日 21:42:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    底部弹上来的添加任务dialog
 */
class TaskBottomSheetDialog : BottomSheetDialogFragment(){

    private var mCalendarData = CalendarData(getYear(), getMonth(), getDay())

    private val viewModel by activityViewModels<TaskViewModel>()

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_task,null)
        dialog.setContentView(view)
        initView(view)
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.BottomSheetDialog)
    }

    override fun onStart() {
        super.onStart()
        if (dialog is BottomSheetDialog) {
            val behaviour = (dialog as BottomSheetDialog).behavior
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun initView(rootView : View){
        //日历选择
        rootView.findViewById<TextView>(R.id.tv_dialog_time).apply{
            text = buildString {
                append(getYear())
                append("-")
                append(getMonth())
                append("-")
                append(getDay())
            }
            setOnClickListener {
                showDatePickerDialog(requireActivity(),5){//选择回调
                    text = buildString {
                        append(it.year)
                        append("-")
                        append(it.month)
                        append("-")
                        append(it.day)
                    }
                }
            }
        }
        val title = rootView.findViewById<EditText>(R.id.et_dialog_title)
        val text = rootView.findViewById<EditText>(R.id.et_dialog_text)
        //保存设置
        rootView.findViewById<MaterialButton>(R.id.btn_dialog_save).apply {
            setOnClickListener {
                if(title.text.isEmpty()){
                    toast("标题不可为空")
                    return@setOnClickListener
                }
                if(text.text.isEmpty()){
                    toast("内容不可为空")
                    return@setOnClickListener
                }
                val date = buildString {
                    append(mCalendarData.year)
                    append("-")
                    append(mCalendarData.month)
                    append("-")
                    append(mCalendarData.day)
                }
                viewModel.addTask(title.text.toString(),text.text.toString(),date,0)
                viewModel.observeAddTaskInfo(requireActivity()){
                    onSuccess {
                        toast("保存成功")
                        closeDialog()
                    }
                    onFailed { _, errMsg ->
                        if(errMsg == null){
                            toast("保存失败，请稍后再试")
                        }
                    }
                }
            }
        }
    }

    /**
     * 日期选择
     */
    private fun showDatePickerDialog(activity: Activity, themeResId: Int,onCalendarSelected : (CalendarData) -> Unit) {
        val dialog = DatePickerDialog(activity,
            themeResId,
            { _, year, monthOfYear, dayOfMonth ->
                // 回调
                mCalendarData = CalendarData(year,monthOfYear+1,dayOfMonth)
                onCalendarSelected(mCalendarData)
            } // 设置初始日期
            , getYear(), getMonth()-1, getDay())
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).gravity = Gravity.END
    }

    /**
     * 关闭dialog
     */
    private fun closeDialog(){
        Handler(Looper.myLooper()!!).postDelayed({
            dialog?.onBackPressed()
        },500)
    }


}