
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.radioistops"
    // CORRECCIÓN: Se usa la sintaxis directa. 35 es la versión estable actual.
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.radioistops"
        minSdk = 30
        // CORRECCIÓN: Ajustado a 35 para que coincida con compileSdk
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
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
        // CORRECCIÓN: Se recomienda Java 17 para proyectos modernos de Compose
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.play.services.wearable)
    implementation(libs.core.splashscreen)

    // Wear Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.wear.compose.material)
    implementation(libs.wear.compose.foundation)
    implementation(libs.wear.compose.navigation)
    implementation(libs.activity.compose)

    // Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    // API & Data
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.coroutines.android)
    implementation(libs.material3)
}