package com.yan.holidaytodo.base

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yan.common.util.GenericityUtils.getGenericClassFromSuperClass

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.base
 * @ClassName:      BaseFragment
 * @Author:         Yan
 * @CreateDate:     2022年07月19日 19:58:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    Fragment基类
 */
abstract class BaseFragment : Fragment() {

    /**
     * 替换 Fragment 的正确用法。
     * 如果不按照正确方式使用，会造成 ViewModel 失效，
     * 你可以写个 demo 看看在屏幕翻转后 Fragment 的 ViewModel 的 hashcode() 值是不是同一个
     */
    private inline fun <reified F : Fragment> replaceFragment(
        @IdRes id: Int,
        fragmentManager: FragmentManager = childFragmentManager,
        func: () -> F
    ): F {
        var fragment = fragmentManager.findFragmentById(id)
        if (fragment !is F) {
            fragment = func.invoke()
            fragmentManager.commit {
                replace(id, fragment)
            }
        }
        return fragment
    }

    /**
     * 尤其注意这个 viewLifecycleOwner
     *
     * Fragment 与 View 的生命周期是不同的，而且一般情况下不会使用到 Fragment 的生命周期
     */
    private fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(viewLifecycleOwner, observer)
    }


}