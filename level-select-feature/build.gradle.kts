plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.muztus.level_select_feature"
    compileSdk = 33

    defaultConfig {
        minSdk = 27
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        kotlinCompilerExtensionVersion = Dependencies.Compose.kotlinCompilerExtensionVersion
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2022.10.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(Dependencies.Compose.material)
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.toolingPreview)
    implementation(Dependencies.Compose.toolingUi)
    implementation(Dependencies.Compose.viewModel)
    implementation(Dependencies.Compose.livedata)
    implementation(Dependencies.Compose.animation)

    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.core)

    implementation(Dependencies.Navigation.fragment)
    implementation(Dependencies.Navigation.ui)


    api(project(":core"))
    api(project(":core-mvi"))
    api(project(":domain-layer"))
}