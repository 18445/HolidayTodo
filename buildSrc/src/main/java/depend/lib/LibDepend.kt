package depend.lib

import depend.*
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

/**
 *
 * @ProjectName:    KtsDemo
 * @Package:        depend.lib
 * @ClassName:      LibDepend
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 00:19:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    所有可能需要的依赖
 */



fun Project.dependLibCommon() {

    apply(plugin = "kotlin-android")
    apply(plugin = "kotlin-kapt")


    dependAndroidView()
    dependAndroidKtx()
    dependCoroutines()
    dependCoroutinesRx3()
    dependEventBus()
    dependGlide()
    dependLifecycleKtx()
    dependLottie()
    dependNetwork()
    dependNetworkInternal()
    dependPaging()
    dependRoom()
    dependRoomRxjava()
    dependRoomPaging()


}