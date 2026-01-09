import org.gradle.api.JavaVersion

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.grapevineindustries.fivecrowns"
    compileSdk = 36

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(project(":common"))

    implementation(libs.navigation.runtime.ktx)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.material3)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.compose.ui.test.junit4)

    testImplementation(testFixtures(project(":fivecrowns")))
    testImplementation(libs.espresso.core)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)

    testFixturesImplementation(libs.compose.ui.test.junit4)
    testFixturesImplementation(libs.espresso.core)
    debugImplementation(libs.compose.ui.tooling)
}