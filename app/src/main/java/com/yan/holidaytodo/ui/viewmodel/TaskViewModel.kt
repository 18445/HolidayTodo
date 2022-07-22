package com.yan.holidaytodo.ui.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.yan.common.network.ApiResponse
import com.yan.holidaytodo.base.BaseViewModel
import com.yan.holidaytodo.bean.net.wanandroid.LoginInfo
import com.yan.holidaytodo.bean.net.wanandroid.RegisterInfo
import com.yan.holidaytodo.bean.net.wanandroid.TaskInfo
import com.yan.holidaytodo.bean.net.wanandroid.TaskList
import com.yan.holidaytodo.net.StateTaskLiveData
import com.yan.holidaytodo.repository.TaskRepository
import kotlinx.coroutines.launch

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.ui.viewmodel
 * @ClassName:      LoginViewModel
 * @Author:         Yan
 * @CreateDate:     2022年07月21日 03:50:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    登录注册ViewModel
 */
class TaskViewModel : BaseViewModel() {


    private val mLoginInfo = StateTaskLiveData<LoginInfo>()

    private val mRegisterInfo = StateTaskLiveData<RegisterInfo>()

    private val mAddTaskInfo = StateTaskLiveData<TaskInfo>()

    private val mUpdateTaskInfo = StateTaskLiveData<TaskInfo>()

    private val mTaskInfoLists = StateTaskLiveData<TaskList>()

    private val mAllTaskList = StateTaskLiveData<TaskList>()

    fun loginIn(username : String,password : String){
        viewModelScope.launch {
            mLoginInfo.value = TaskRepository.login(username, password)
        }
    }

    fun register(username: String, password: String, repassword : String){
        viewModelScope.launch {
            mRegisterInfo.value = TaskRepository.register(username, password, repassword)
        }
    }

    fun addTask(title: String,content: String,date: String,type: Long,priority: Int = 0){
        viewModelScope.launch {
            mAddTaskInfo.value = TaskRepository.addTodo(title, content, date, type, priority)
        }
    }

    fun updateTodo(id: Int,title: String,content: String, date : String,status: Int,type: Long,priority: Int = 0){
        viewModelScope.launch{
            mUpdateTaskInfo.value = TaskRepository.updateTodo(id, title, content, date, status, type, priority)
        }
    }

    fun finishTodo(taskInfo : TaskInfo, status : Int){
        viewModelScope.launch {
            mUpdateTaskInfo.value = TaskRepository.updateTodo(taskInfo.id,taskInfo.title,taskInfo.content,taskInfo.dateStr,status,taskInfo.type.toLong(),taskInfo.priority)
        }
    }

    fun deleteTask(id: Int){
        //删除接口好像不好使了 寄
        viewModelScope.launch{
            TaskRepository.deleteTodo(id)
        }
    }

    fun queryTodoList(status: Int, type: Long, priority: Int, index: Int) {
        viewModelScope.launch{
            mTaskInfoLists.value = TaskRepository.queryTodoList(status, type, priority, index)
        }
    }

    fun queryAllTodoList(index : Int){
        viewModelScope.launch {
            mAllTaskList.value = TaskRepository.queryAllTodoList(index)
        }
    }

    fun observeAddTaskInfo(owner : LifecycleOwner, listenerBuilder: StateTaskLiveData<TaskInfo>.ListenerBuilder.() -> Unit){
        observeStateTaskLiveData(mAddTaskInfo,owner,listenerBuilder)
    }

    fun observeUpdateTaskInfo(owner : LifecycleOwner, listenerBuilder: StateTaskLiveData<TaskInfo>.ListenerBuilder.() -> Unit){
        observeStateTaskLiveData(mUpdateTaskInfo,owner,listenerBuilder)
    }

    fun observeTaskList(owner: LifecycleOwner,listenerBuilder: StateTaskLiveData<TaskList>.ListenerBuilder.() -> Unit){
        observeStateTaskLiveData(mTaskInfoLists,owner,listenerBuilder)
    }

    fun observeAllTasks(owner: LifecycleOwner,listenerBuilder: StateTaskLiveData<TaskList>.ListenerBuilder.() -> Unit){
        observeStateTaskLiveData(mAllTaskList,owner,listenerBuilder)
    }

    fun observeLoginInfo(owner : LifecycleOwner, listenerBuilder: StateTaskLiveData<LoginInfo>.ListenerBuilder.() -> Unit){
        observeStateTaskLiveData(mLoginInfo,owner,listenerBuilder)
    }

    fun observeRegister(owner : LifecycleOwner, listenerBuilder: StateTaskLiveData<RegisterInfo>.ListenerBuilder.() -> Unit){
        observeStateTaskLiveData(mRegisterInfo,owner,listenerBuilder)
    }


}