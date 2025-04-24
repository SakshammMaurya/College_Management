plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.compose.compiler)
   // alias(libs.plugins.compose.compiler) apply false
   // alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.example.collegemanagement"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.collegemanagement"
        minSdk = 26
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

//    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.storage)
    //implementation platform("com.google.firebase:firebase-bom:33.9.0")
    // implementation(libs.androidx.navigation.dynamic.features.fragment)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(platform(libs.firebase.bom))


    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.constraintlayout.compose)

    // pages and indicatiors
    implementation(libs.accompanist.pager)
//    implementation(libs.accompanist.pager.indicators)
//    implementation(libs.androidx.foundation.layout)
//    implementation(libs.google.accompanist.pager)

    //coil dependencies
//    implementation(libs.coil.compose)
//    implementation(libs.coil) // or whatever version you're using
    //implementation(libs.coil.kt.coil.compose)
//    implementation(libs.coil.gif) // optional, if you use GIFs

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

}