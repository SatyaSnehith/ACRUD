import com.satyasnehith.acrud.buildSrc.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("kapt")
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
//    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "com.satyasnehith.acrud.core.network"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        minSdk = Configuration.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    implementation(libs.moshi)
    kapt(libs.moshi.codegen)

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.okhttp)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

}