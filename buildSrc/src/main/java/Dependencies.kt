object Dependencies {
    object Kotlin {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2"
        const val kotlinCore = "org.jetbrains.kotlin:kotlin-stdlib:1.7.20"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val material = "com.google.android.material:material:1.4.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-beta1"
        const val fragment = "androidx.activity:activity-ktx:1.2.2"
        const val activity = "androidx.fragment:fragment-ktx:1.3.2"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val security = "androidx.security:security-crypto:1.0.0-rc04"

    }

    object GoogleAd {
        const val mobileAd = "com.google.android.gms:play-services-ads:21.0.0"
        const val billing = "com.android.billingclient:billing-ktx:5.0.0"
    }

    object Navigation {
        private const val navVersion = "2.5.3"
        const val fragment = "androidx.navigation:navigation-fragment:$navVersion"
        const val ui = "androidx.navigation:navigation-ui:$navVersion"
        const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$navVersion"
        const val uiKtx = "androidx.navigation:navigation-ui-ktx:$navVersion"
        const val navigationDynamicFeatures = "androidx.navigation:navigation-dynamic-features-fragment:$navVersion"
    }

    object Compose {
        private const val composeVersion = "1.1.1"
        const val kotlinCompilerExtensionVersion = "1.3.2"

        const val material = "androidx.compose.material:material"
        const val ui = "androidx.compose.ui:ui"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview"
        const val toolingUi = "androidx.compose.ui:ui-tooling"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
        const val livedata = "androidx.compose.runtime:runtime-livedata"
        const val animation = "androidx.compose.animation:animation:1.4.0-alpha01"
        const val activity = "androidx.activity:activity-compose:1.4.0"

        // UI Tests
//        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//        debugImplementation("androidx.compose.ui:ui-test-manifest")
    }

    object Koin {
        private const val koinComposeVersion = "3.2.0"
        const val core = "io.insert-koin:koin-core:$koinComposeVersion"
        const val android = "io.insert-koin:koin-android:$koinComposeVersion"

    }

    object Room {
        private const val roomVersion = "2.5.1"

        const val runTime = "androidx.room:room-runtime:$roomVersion"
        const val compiler = "androidx.room:room-compiler:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    }
}
