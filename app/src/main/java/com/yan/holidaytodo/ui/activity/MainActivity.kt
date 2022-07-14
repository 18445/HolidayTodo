package com.yan.holidaytodo.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.yan.common.base.BaseActivity
import com.yan.holidaytodo.R
import com.yan.holidaytodo.net.ApiService
import com.yan.holidaytodo.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            ApiService.Instance.getDayInfo("2022")
        }
    }

}