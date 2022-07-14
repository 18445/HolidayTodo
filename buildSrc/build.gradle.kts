plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    groovy
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    }
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    compileOnly(gradleApi())
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
    compileOnly("com.android.tools.build:gradle:7.0.3")
}

gradlePlugin {
    plugins {
        create("version") {
            id = "versionConfig"
            implementationClass = "depend.lib.VersionConfigPlugin"
        }
    }
}