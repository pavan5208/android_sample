plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions")

}

android {
    compileSdkVersion(ConfigData.compileSdk)
    buildToolsVersion(ConfigData.buildTools)

    defaultConfig {
        applicationId = "com.loco.movie.list"
        minSdkVersion(ConfigData.minSdk)
        targetSdkVersion(ConfigData.targetSdk)
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation(AppDependencies.Deps.Kotlin.kotlin)
    implementation(AppDependencies.Deps.Kotlin.kotlinCoreKtx)
    implementation(AppDependencies.Deps.Kotlin.kotlinLifecycleKtx)

    implementation(AppDependencies.Deps.AndroidX.appCompat)
    implementation(AppDependencies.Deps.AndroidX.recyclerView)
    implementation(AppDependencies.Deps.AndroidX.cardView)
    implementation(AppDependencies.Deps.Others.materialDesign)

    implementation(AppDependencies.Deps.Others.shimmer)
    implementation(AppDependencies.Deps.Others.timber)
    implementation(AppDependencies.Deps.Others.constraintLayout)
    implementation(AppDependencies.Deps.Others.glideRuntime)
    kapt(AppDependencies.Deps.Others.glideCompiler)
    implementation(AppDependencies.Deps.Kotlin.kotlinFragmentKtx)

    //Netwokring
    implementation(AppDependencies.Deps.Networking.retrofitRuntime)
    implementation(AppDependencies.Deps.Networking.retrofitGson)
    implementation(AppDependencies.Deps.Networking.okhttp)
    implementation(AppDependencies.Deps.Networking.okhttpLoggingInterceptor)

    //Dagger
    implementation(AppDependencies.Deps.Dagger.daggerRuntime)
    implementation(AppDependencies.Deps.Dagger.daggerAndroid)
    implementation(AppDependencies.Deps.Dagger.daggerAndroidSupport)
    kapt(AppDependencies.Deps.Dagger.daggerCompiler)
    kapt(AppDependencies.Deps.Dagger.daggerProcessor)

    testImplementation(AppDependencies.Deps.Testing.junit)
    androidTestImplementation(AppDependencies.Deps.Testing.atslExtJunit)
    androidTestImplementation(AppDependencies.Deps.Testing.espressoCore)
}