plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    val SDK_VERSION = 33
    namespace = "me.zzhhoo.bilibili"
    compileSdk = SDK_VERSION
    defaultConfig {
        applicationId = "me.zzhhoo.bilibili"
        minSdk = 29
        targetSdk = SDK_VERSION
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    val DATASTORE_VERSION = "1.0.0"
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.datastore:datastore-preferences:${DATASTORE_VERSION}")
    // Http库OkHttp <https://github.com/square/okhttp>
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    // Log库Logger <https://github.com/orhanobut/logger>
    implementation("com.orhanobut:logger:2.2.0")
    // https://github.com/zhihaofans/AndroidLibrary
    debugImplementation(files("libs/android_library-debug.aar"))
    releaseImplementation("io.zhihao:AndroidLibrary:0.0.11-jitpackfix-2")
    implementation("com.google.code.gson:gson:2.10.1")
    // 图片加载库Coil <https://github.com/coil-kt/coil>
    implementation("io.coil-kt:coil-compose:2.4.0")

    // RxTool <https://github.com/vondear/RxTool>
//    val rxtool_version = "2.6.3"
//    implementation("com.github.vondear.RxTool:RxKit:$rxtool_version")
//    implementation("com.github.vondear.RxTool:RxUI:$rxtool_version")
//    implementation("com.github.vondear.RxTool:RxCamera:$rxtool_version")
//    implementation("com.github.vondear.RxTool:RxFeature:$rxtool_version")

    // 二维码库Zxing <https://github.com/zxing/zxing>
    // 使用参考 <https://github.com/Tamsiree/RxTool/blob/master/RxFeature/src/main/java/com/tamsiree/rxfeature/tool/RxQRCode.kt>
    implementation("com.google.zxing:core:3.5.2")



}