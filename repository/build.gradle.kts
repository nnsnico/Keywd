plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    val versions = libs.versions

    namespace = versions.packageName.get() + "repository"
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
    implementation(project(":core"))
    implementation(project(":model"))
    implementation(project(":datasource"))

    implementation(libs.androidx.core.ktx)

    implementation(libs.google.hilt.android)
    kapt(libs.google.hilt.compiler)

    platform(libs.arrow.kt.arrow.stack)
    implementation(libs.arrow.kt.arrow.core)

    testImplementation(libs.junit.junit)
    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.agent)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
}
