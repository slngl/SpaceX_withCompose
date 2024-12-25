plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.slngl.spacexwithcompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.slngl.spacexwithcompose"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.hilt.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
//    implementation (libs.androidx.hilt.lifecycle.viewmodel)

// Retrofit
    implementation(libs.retrofit)

// okHttp
    implementation(libs.okhttp.logging)

// Gson Convertor
    implementation(libs.converter.gson)

// Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

// Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

// Navigation
    implementation(libs.androidx.navigation.compose)
// Hilt Navigation
    implementation(libs.androidx.hilt.navigation.compose)

// preferences datastore
    implementation(libs.androidx.datastore.preferences)

// Splash screen
    implementation(libs.androidx.core.splashscreen)

// Material icons
    implementation(libs.androidx.material.icons.extended.android)

// Coil
    implementation(libs.coil.compose)

// Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

// Coroutines
    implementation(libs.androidx.room.ktx)
}

//// Allow references to generated code
//kapt {
//    correctErrorTypes = true
//}