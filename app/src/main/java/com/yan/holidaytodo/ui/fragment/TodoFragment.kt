package com.yan.holidaytodo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yan.holidaytodo.R
import com.yan.holidaytodo.base.BaseFragment

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.ui.fragment
 * @ClassName:      TodoFragment
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 19:55:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    任务Fragment
 */
class TodoFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_todo,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}