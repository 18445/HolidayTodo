package com.yan.holidaytodo.ui.activity

import android.os.Bundle
import android.widget.ImageButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.yan.common.extension.toast
import com.yan.common.extension.toastLong
import com.yan.holidaytodo.R
import com.yan.holidaytodo.base.BaseActivity
import com.yan.holidaytodo.ui.viewmodel.TaskViewModel
import com.yan.holidaytodo.util.validateText

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.ui.activity
 * @ClassName:      RegisterActivity
 * @Author:         Yan
 * @CreateDate:     2022年07月21日 04:06:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    WanAndroid注册Activity
 */
class RegisterActivity : BaseActivity<TaskViewModel>() {

    private lateinit var mImageButton : ImageButton

    private lateinit var mRegisterButton : MaterialButton

    private lateinit var mUsernameTextInputEditText: TextInputEditText
    private lateinit var mPasswordTextInputEditText: TextInputEditText
    private lateinit var mRepasswordTextInputEditText: TextInputEditText

    private lateinit var mUsernameTextInputLayout: TextInputLayout
    private lateinit var mPasswordTextInputLayout: TextInputLayout
    private lateinit var mRepasswordTextInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView(){
        mUsernameTextInputEditText = findViewById(R.id.tie_register_account)
        mPasswordTextInputEditText = findViewById(R.id.tie_register_password)
        mRepasswordTextInputEditText = findViewById(R.id.tie_register_repassword)

        mUsernameTextInputLayout = findViewById(R.id.til_register_account)
        mPasswordTextInputLayout = findViewById(R.id.til_register_password)
        mRepasswordTextInputLayout = findViewById(R.id.til_register_repassword)

        mImageButton = findViewById<ImageButton?>(R.id.ib_register_back).apply {
            setOnClickListener {
                this@RegisterActivity.finish()
            }
        }

        mRegisterButton = findViewById<MaterialButton?>(R.id.btn_register).apply {
            setOnClickListener {
                val username = mUsernameTextInputEditText.text.toString()
                val password = mPasswordTextInputEditText.text.toString()
                val repassword = mRepasswordTextInputEditText.text.toString()
                if(mUsernameTextInputLayout.validateText(username,"账户不可以为空") &&
                        mPasswordTextInputLayout.validateText(password,"密码不可以为空") &&
                        mRepasswordTextInputLayout.validateText(repassword,"密码不可以为空")){
                        register(username, password, repassword)
                }
            }
        }
    }

    private fun register(username : String,password : String ,repassword : String){
        viewModel.register(username, password, repassword)
        viewModel.mRegisterInfo.observeState(this){
            onSuccess {
                toastLong("注册成功")
            }
            onFailed{ _, errMsg ->
                if (errMsg == null){
                    toast("注册失败，请稍后再试")
                }
            }
        }
    }




}