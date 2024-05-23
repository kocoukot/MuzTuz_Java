plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.muztus.statistic"
    compileSdk = 33

    defaultConfig {
        minSdk = 27
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.kotlinCompilerExtensionVersion
    }
}

dependencies {
    implementation(Dependencies.Kotlin.kotlinCore)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.constraint)
    implementation(Dependencies.AndroidX.fragment)
    implementation(Dependencies.AndroidX.activity)
    implementation(Dependencies.AndroidX.lifecycle)
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

    implementation(Dependencies.Navigation.fragment)
    implementation(Dependencies.Navigation.ui)

    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    api(project(":core"))
    api(project(":core-mvi"))
    api(project(":domain-layer"))

}