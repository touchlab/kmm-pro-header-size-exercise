plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.android.library)

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

kotlin {
    jvmToolchain(11)

    androidTarget()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            isStatic = false
            linkerOpts("-lsqlite3")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
            implementation(libs.sqlDelight.coroutinesExt)
            implementation(libs.touchlab.kermit)
        }
        androidMain.dependencies {
            implementation(libs.sqlDelight.android)
        }
        iosMain.dependencies {
            implementation(libs.sqlDelight.native)
        }
    }
}

sqldelight {
    databases.create("KaMPKitDb") {
        packageName.set("co.touchlab.kampkit.db")
    }
} 