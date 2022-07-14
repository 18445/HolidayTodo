package com.yan.common.base

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yan.common.network.ApiResponse
import com.yan.common.util.GenericityUtils.getGenericClassFromSuperClass
import kotlinx.coroutines.flow.Flow

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.common.base
 * @ClassName:      BaseActivity
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 14:08:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    BaseActivity
 */
abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {
    fun showLoading() {

    }

    fun dismissLoading(){

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        cancelStatusBar()
    }

    //透明StatusBar
    private fun cancelStatusBar() {
        val window = this.window
        val decorView = window.decorView

        // 这是 Android 做了兼容的 Compat 包
        // 注意，使用了下面这个方法后，状态栏不会再有东西占位，
        // 可以给根布局加上 android:fitsSystemWindows=true
        // 不同布局该属性效果不同，请给合适的布局添加
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController = ViewCompat.getWindowInsetsController(decorView)
        windowInsetsController?.isAppearanceLightStatusBars = true // 设置状态栏字体颜色为黑色
        window.statusBarColor = Color.TRANSPARENT //把状态栏颜色设置成透明
    }

    protected val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        val factory = getViewModelFactory()
        if (factory == null) {
            ViewModelProvider(this)[getGenericClassFromSuperClass(javaClass)] as VM
        } else {
            ViewModelProvider(this, factory)[getGenericClassFromSuperClass(javaClass)] as VM
        }
    }

    protected open fun getViewModelFactory(): ViewModelProvider.Factory? = null

}

