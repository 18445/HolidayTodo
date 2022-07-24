import depend.lib.dependLibCommon

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = depend.lib.VersionConfig.compileSdk

    defaultConfig {
        applicationId = depend.lib.VersionConfig.applicationId
        minSdk = depend.lib.VersionConfig.minSdk
        targetSdk = depend.lib.VersionConfig.targetSdk
        versionCode = depend.lib.VersionConfig.versionCode
        versionName = depend.lib.VersionConfig.versionName

        testInstrumentationRunner = "androidx.test.runne r.AndroidJUnitRunner"
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    kotlinOptions {
        jvmTarget = depend.lib.VersionConfig.jvmTarget
    }
}

dependencies {
    implementation(project(":common"))
    implementation(kotlin("reflect"))
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.0")
}


dependLibCommon()