plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("io.realm.kotlin")
    id("realm-android")
}

android {
    namespace = "com.example.shopeee"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shopeee"
        minSdk = 26
        targetSdk = 35
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "18"
    }

    buildFeatures {
        viewBinding = true
    }
}

configurations.all {
    resolutionStrategy {
        force("androidx.core:core:1.13.1")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

realm {
    isSyncEnabled = true
}

dependencies {
    // Basic Android
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")

    // Test dependencies
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Intuit
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("com.intuit.ssp:ssp-android:1.0.6")

    // Firebase
    implementation("com.google.firebase:firebase-firestore:24.0.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.0.1")
    implementation("com.google.firebase:firebase-auth-ktx:21.0.1")
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-storage-ktx:20.0.0")
    implementation("com.google.firebase:firebase-common-ktx:20.0.0")
    implementation("com.google.firebase:firebase-messaging-ktx:23.0.0")

    // Google Play Services
    implementation("com.google.android.gms:play-services-auth:20.1.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.13.0")

    // Circular image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Navigation and safe args
    val navVersion = "2.4.1"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Firebase coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1")

    // Android Ktx
    implementation("androidx.fragment:fragment-ktx:1.4.1")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    annotationProcessor("com.google.dagger:hilt-compiler:2.38.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
