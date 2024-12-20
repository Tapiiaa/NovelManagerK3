plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.novelmanager"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.novelmanager"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    // Repositorios deben ir configurados en settings.gradle.kts
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
}

dependencies {
    // AndroidX dependencies
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Room database dependencies
    implementation("androidx.room:room-runtime:2.5.2")
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    implementation(libs.androidx.sqlite)
    annotationProcessor("androidx.room:room-compiler:2.3.0")

    // ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    // Preference and layout libraries
    implementation(libs.androidx.preference)
    implementation(libs.androidx.foundation.layout.android)

    // Unit testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // WorkManager for background tasks
    implementation("androidx.work:work-runtime:2.8.1")
}
