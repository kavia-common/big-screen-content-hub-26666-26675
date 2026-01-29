pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.experimental.android-ecosystem").version("0.1.43")
}

rootProject.name = "example-android-app"

include("app")
include("list")
include("utilities")

defaults {
    androidApplication {
        // Android 14 / API 34 upgrade: keep JDK 17 and raise/retain compileSdk 34 for modern compliance.
        jdkVersion = 17
        compileSdk = 34
        // Reasonable Android TV/STB baseline; keep low unless app code requires higher.
        minSdk = 24

        versionCode = 1
        versionName = "0.1"
        applicationId = "org.gradle.experimental.android.app"

        testing {
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter:5.10.2")
                runtimeOnly("org.junit.platform:junit-platform-launcher")
            }
        }
    }

    androidLibrary {
        // Android 14 / API 34 upgrade alignment for library modules.
        jdkVersion = 17
        compileSdk = 34
        minSdk = 24

        testing {
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter:5.10.2")
                runtimeOnly("org.junit.platform:junit-platform-launcher")
            }
        }
    }
}
