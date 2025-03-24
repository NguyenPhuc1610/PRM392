//plugins {
//    id("com.android.application")
//    id("com.google.gms.google-services")
//}
//
//android {
//    namespace = "com.fpt.phucnth.assigmentfinal"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.fpt.phucnth.assigmentfinal"
//        minSdk = 27
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//}
//
//dependencies {
//
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.11.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    implementation("com.google.firebase:firebase-database:21.0.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//
//    implementation("androidx.recyclerview:recyclerview:1.3.2")
//    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
//
//}


plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // Plugin Google Services để sử dụng Firebase
}

android {
    namespace = "com.fpt.phucnth.assigmentfinal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fpt.phucnth.assigmentfinal"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Firebase dependencies
    implementation("com.google.firebase:firebase-auth:22.1.2") // Cập nhật Firebase Authentication mới nhất
    implementation("com.google.firebase:firebase-database:21.0.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // RecyclerView & Chart Library
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}
