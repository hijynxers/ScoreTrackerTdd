import org.gradle.api.JavaVersion

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.kotlin.ksp)
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
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions
            .jvmTarget
            .set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
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

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    testImplementation(project(":common"))
    testImplementation(testFixtures(project(":fivecrowns")))
    testImplementation(testFixtures(project(":common")))
    testImplementation(libs.espresso.core)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.test.core)

    testFixturesImplementation(libs.compose.ui.test.junit4)
    testFixturesImplementation(libs.espresso.core)
    testFixturesImplementation(project(":common"))
    debugImplementation(libs.compose.ui.tooling)
}
