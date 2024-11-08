plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("dagger.hilt.android.plugin")


}

android {
    namespace = "kis.kan.jetreadearapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "kis.kan.jetreadearapp"
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {

    implementation(platform(libs.firebase.bom))

    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)

    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-firestore")


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
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)




    // Hilt Android dependency
    implementation(libs.hilt.android)

    // Hilt compiler
    kapt(libs.hilt.android.compiler.v248)

    // Hilt ViewModel extension
//    implementation(libs.androidx.hilt.lifecycle.viewmodel)
    kapt(libs.androidx.hilt.compiler)

    // (Optional) For Jetpack Compose integration
    implementation(libs.androidx.hilt.navigation.compose)

    // coroutine dependency
    implementation(libs.kotlinx.coroutines.android)

    // coroutine view model
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)


    implementation("io.coil-kt.coil3:coil-compose:3.0.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.0")

}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}