package com.yan.plugin



import com.android.build.gradle.*
import depend.lib.VersionConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType


/**
 *
 * @ProjectName:    KtsDemo
 * @Package:        depend.lib
 * @ClassName:      AppDepend
 * @Author:         Yan
 * @CreateDate:     2022年07月14日 01:15:00
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 * @Description:    寄中寄
 */

class VersionConfigPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.config(project)
    }

    private fun PluginContainer.config(project: Project) {
        whenPluginAdded {
            when(this){
            is AppPlugin -> {
                project.extensions.getByType<AppExtension>().applyBaseCommons(project)
            }
        }
        }
    }

    private fun BaseExtension.applyBaseCommons(project: Project) {
        compileSdkVersion(VersionConfig.compileSdk)

        defaultConfig {
            minSdk = VersionConfig.minSdk
            targetSdk = VersionConfig.targetSdk
            versionCode = VersionConfig.versionCode
            versionName = VersionConfig.versionName
            testInstrumentationRunner = VersionConfig.runner
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        buildFeatures.viewBinding = true

        project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}