 plugins {
    id("com.android.application")
}


android {
    namespace = "gmail.com.qlcafepoly"
    compileSdk = 33
    defaultConfig {
        applicationId = "gmail.com.qlcafepoly"
        minSdk = 22
        targetSdk = 33
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
}

dependencies {

    implementation("com.android.volley:volley:1.2.1")

    //noinspection GradleCompatible,GradleCompatible,GradleCompatible,GradleCompatible,GradleCompatible,GradleCompatible
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//    implementation("com.google.android.gms:play-services-pay:16.4.0")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.squareup.retrofit2:retrofit:2.3.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:3.4.1")

    implementation ("me.relex:circleindicator:2.1.6")
    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("com.journeyapps:zxing-android-embedded:3.6.0")
    implementation ("com.google.zxing:core:3.4.0")
}



