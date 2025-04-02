plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
}

kotlin {
    jvmToolchain(11)

    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "database"
            isStatic = false
            linkerOpts("-lsqlite3")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.sqlDelight.coroutinesExt)
            implementation(libs.kotlinx.dateTime)
            implementation(libs.multiplatformSettings.common)
            implementation(libs.touchlab.kermit)
            implementation(libs.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.multiplatformSettings.test)
        }

        androidMain.dependencies {
            implementation(libs.sqlDelight.android)
        }

        androidUnitTest.dependencies {
            implementation(libs.roboelectric)
            implementation(libs.androidx.test.junit)
            implementation(libs.sqlDelight.jvm)
        }

        iosMain.dependencies {
            implementation(libs.sqlDelight.native)
        }

        iosTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.sqlDelight.native)
        }
    }
}

sqldelight {
    databases {
        create("KaMPKitDb") {
            packageName.set("co.touchlab.kampkit.db")
        }
    }
}

android {
    namespace = "co.touchlab.kampkit.database"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true
    }
} 