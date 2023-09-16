plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    val versions = libs.versions

    namespace = versions.packageName.get() + "ui.core"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versions.composeCompiler.get()
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.collections.immutable)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.google.accompanist.permission)

    platform(libs.arrow.kt.arrow.stack)
    implementation(libs.arrow.kt.arrow.core)

    lintChecks(libs.slack.compose.lint)

    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
    androidTestImplementation(composeBom)
}
