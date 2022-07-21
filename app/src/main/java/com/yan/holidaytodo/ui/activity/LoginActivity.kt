package com.yan.holidaytodo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
 * @ClassName:      LoginActivity
 * @Author:         Yan
 * @CreateDate:     2022年07月21日 02:34:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    WanAndroid登录Activity
 */
class LoginActivity : BaseActivity<TaskViewModel>() {

    private lateinit var mTextView : TextView

    private lateinit var mUsernameEditText: TextInputEditText

    private lateinit var mPasswordEditText: TextInputEditText

    private lateinit var mUsernameTextInputLayout: TextInputLayout

    private lateinit var mPasswordTextInputLayout: TextInputLayout

    private lateinit var mButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initTextView()
        initButton()
    }

    private fun initTextView(){
        mTextView = findViewById<TextView?>(R.id.tv_login_toRegister).apply {
            setOnClickListener {
                startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
            }
        }

        mUsernameEditText = findViewById(R.id.tie_login_account)
        mPasswordEditText = findViewById(R.id.tie_login_password)

        mUsernameTextInputLayout = findViewById(R.id.til_login_account)
        mPasswordTextInputLayout = findViewById(R.id.til_login_password)
    }

    private fun initButton(){
        mButton = findViewById<Button?>(R.id.btn_login).apply {
            setOnClickListener {
                val username = mUsernameEditText.text.toString()
                val password = mPasswordEditText.text.toString()
                if(mUsernameTextInputLayout.validateText(username,"账户不能为空") &&
                    mPasswordTextInputLayout.validateText(password,"密码不能为空")){
                    loginIn(username,password)
                }
            }
        }
    }

    private fun loginIn(username : String, password : String){
        viewModel.loginIn(username , password)
        viewModel.observeLoginInfo(this){
            onSuccess {
                toastLong("登录成功")
                finish()
            }
            onFailed { _, errMsg ->
                if (errMsg == null){
                    toast("登录失败，请稍后再试")
                }
            }
        }
    }




}