plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    val versions = libs.versions

    namespace = versions.packageName.get() + "model"
    compileSdk = versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = versions.minSdk.get().toInt()
        multiDexEnabled = true

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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.toVersion(versions.compileJvm.get())
        targetCompatibility = JavaVersion.toVersion(versions.compileJvm.get())
    }
    kotlinOptions {
        jvmTarget = versions.compileJvm.get()
    }
}

dependencies {
    implementation(project(":core"))

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.androidx.core.ktx)

    platform(libs.arrow.kt.arrow.stack)
    implementation(libs.arrow.kt.arrow.core)
    implementation(libs.androidx.room.runtime)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.junit)
}
