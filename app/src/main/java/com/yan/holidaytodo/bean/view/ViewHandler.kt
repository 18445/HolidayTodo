package com.yan.holidaytodo.bean.view

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import java.lang.ref.WeakReference

/**
 *
 * @ProjectName:    HolidayTodo
 * @Package:        com.yan.holidaytodo.bean.view
 * @ClassName:      ViewHandler
 * @Author:         Yan
 * @CreateDate:     2022年07月20日 19:15:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    ViewHandler
 */
class ViewHandler(view: View) : Handler(Looper.getMainLooper()) {
    var mView: WeakReference<View> = WeakReference(view)
    override fun handleMessage(msg: Message) {
        val theView: View?= mView.get()
        if (theView == null || !theView.isAttachedToWindow) {
            return
        }
        val downTime: Long = SystemClock.uptimeMillis()
        val bean = msg.obj as GestureBean
        val count = bean.count
        if (count >= bean.totalCount) {
            theView.onTouchEvent(MotionEvent.obtain(downTime,
                downTime,
                MotionEvent.ACTION_UP,
                bean.endX,
                bean.endY,
                0))
        } else {
            theView.onTouchEvent(MotionEvent.obtain(downTime,
                downTime,
                MotionEvent.ACTION_MOVE,
                bean.startX + bean.ratioX * count,
                bean.startY + bean.ratioY * count,
                0))
            bean.count++
            val message = Message()
            message.obj = bean
            sendMessageDelayed(message, bean.period.toLong())
        }
    }

}
