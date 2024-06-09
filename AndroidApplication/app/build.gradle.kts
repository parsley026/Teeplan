plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.googleGmsGoogleServices)
}

android {
    namespace = "com.example.teeplan"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.teeplan"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packaging {
        resources {
            excludes += "META-INF/NOTICE.md"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE.txt"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.speed.dial)

    implementation(files("libs/activation.jar"))
    implementation(files("libs/additionnal.jar"))
    implementation(files("libs/mail.jar"))
}