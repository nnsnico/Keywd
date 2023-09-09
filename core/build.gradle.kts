plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    val versions = libs.versions

    namespace = versions.packageName.get() + "core"
    compileSdk = versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(versions.compileJvm.get())
        targetCompatibility = JavaVersion.toVersion(versions.compileJvm.get())
    }
    kotlinOptions {
        jvmTarget = versions.compileJvm.get()
    }
}

dependencies {

    platform(libs.kotlin.bom)
    implementation(libs.kotlin.stdlib)

    platform(libs.arrow.kt.arrow.stack)
    implementation(libs.arrow.kt.arrow.core)

    testImplementation(libs.junit.junit)

}
