import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("kapt")

}


fun getKeystorePath(): String {
    if (Os.isFamily(Os.FAMILY_WINDOWS)) return "c:/Keystore/MuzTuskey"
    if (Os.isFamily(Os.FAMILY_MAC)) return "${System.getProperties()["user.home"]}/Keystore/MuzTuskey"
    return System.getenv("CIVILCAM_KEYSTORE_PATH")
}


android {
    namespace = "com.artline.muztus"
    signingConfigs {
        create("release") {
            storeFile = file(getKeystorePath())
            storePassword = ("Jessob19")
            keyAlias = ("MuzTuskey")
            keyPassword = ("geNii309")
        }
    }
    compileSdk = 33
    defaultConfig {
        applicationId = "com.artline.muztus"
        minSdk = 27
        targetSdk = 33
        versionCode = 5
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.findByName("release")
        }
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

//    implementation (fileTree(dir: 'libs', include: ['*.jar']))

    implementation(Dependencies.Kotlin.kotlinCore)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.material)
    implementation(Dependencies.AndroidX.constraint)
    implementation(Dependencies.AndroidX.fragment)
    implementation(Dependencies.AndroidX.activity)
    implementation(Dependencies.AndroidX.lifecycle)
    implementation(Dependencies.AndroidX.security)


    implementation(Dependencies.Navigation.fragment)
    implementation(Dependencies.Navigation.ui)

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
    implementation(Dependencies.Compose.activity)

    implementation(Dependencies.GoogleAd.mobileAd)

    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")


    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    api(project(":statistic-feature"))
    api(project(":creators-feature"))
    api(project(":level-select-feature"))
    api(project(":premium-select-feature"))
    api(project(":shop-feature"))

    api(project(":core"))
    api(project(":core-mvi"))
    api(project(":domain-layer"))


}
