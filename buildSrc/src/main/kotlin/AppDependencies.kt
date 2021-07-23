class AppDependencies {

    /**
     * Root level build.gradle.kts file
     */
    object BuildPlugins {
        val gradle by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
        val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinPlugin}" }
    }

    object Deps{

        object AndroidX {
            val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
            val recyclerView by lazy { "androidx.recyclerview:recyclerview:${Versions.recyclerView}" }
            val cardView by lazy { "androidx.cardview:cardview:${Versions.support}" }
        }

        object Networking {
            val retrofitRuntime by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
            val retrofitGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverts}" }
            val okhttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttp}" }
            val okhttpLoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}" }
        }

        object Kotlin {
            val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinPlugin}" }
            val kotlinCoreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
            val kotlinFragmentKtx by lazy { "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}" }
            val kotlinLifecycleKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleKtx}" }
            val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
            val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
        }

        object Dagger {
            val daggerRuntime by lazy { "com.google.dagger:dagger:${Versions.dagger}" }
            val daggerAndroid by lazy { "com.google.dagger:dagger-android:${Versions.dagger}" }
            val daggerCompiler by lazy { "com.google.dagger:dagger-compiler:${Versions.dagger}" }
            val daggerProcessor by lazy { "com.google.dagger:dagger-android-processor:${Versions.dagger}" }
            val daggerAndroidSupport by lazy { "com.google.dagger:dagger-android-support:${Versions.dagger}" }
        }

        object Others {
            val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
            val shimmer by lazy { "com.facebook.shimmer:shimmer:${Versions.shimmer}" }
            val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
            val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
            val glideRuntime by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
            val glideCompiler by lazy { "com.github.bumptech.glide:compiler:${Versions.glide}" }

        }
        object Testing {
            val junit by lazy { "junit:junit:${Versions.jUnit}" }
            val atslExtJunit by lazy { "androidx.test.ext:junit:${Versions.atslJunit}" }
            val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
        }
    }
}