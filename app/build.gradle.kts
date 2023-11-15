import com.satyasnehith.acrud.buildSrc.Configuration

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("kapt")
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.hilt.plugin.get().pluginId)
//    id(libs.plugins.ksp.get().pluginId)
}

android {
    namespace = "com.satyasnehith.acrud"
    compileSdk = Configuration.compileSdk

    defaultConfig {
        applicationId = "com.satyasnehith.acrud"
        minSdk = Configuration.minSdk
        targetSdk = Configuration.targetSdk
        versionCode = Configuration.versionCode
        versionName = Configuration.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {
    implementation(project(":core-network"))
    implementation(libs.timber)

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.moshi)
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.okhttp)

    implementation(libs.coroutines)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("org.mockito:mockito-android:5.7.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    debugImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}