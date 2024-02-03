enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }

    versionCatalogs {
        create("libs") {
            version("kotlin", "1.7.20")
            plugin("kotlin-serialization","org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")

            library("kotlin-coroutines","org.jetbrains.kotlinx", "kotlinx-coroutines-core").version("1.6.4")

            version("compose-compiler", "1.3.2")

            version("compose", "1.3.0-rc01")
            library("compose-foundation", "androidx.compose.foundation", "foundation").versionRef("compose")
            library("compose-ui", "androidx.compose.ui", "ui").versionRef("compose")
            library("compose-ui-util", "androidx.compose.ui", "ui-util").versionRef("compose")
            library("compose-ripple", "androidx.compose.material", "material-ripple").versionRef("compose")

            library("compose-shimmer", "com.valentinilk.shimmer", "compose-shimmer").version("1.0.3")

            library("compose-activity", "androidx.activity", "activity-compose").version("1.7.0-alpha01")

            library("compose-coil", "io.coil-kt", "coil-compose").version("2.2.2")

            version("room", "2.5.0-beta01")
            library("room", "androidx.room", "room-ktx").versionRef("room")
            library("room-compiler", "androidx.room", "room-compiler").versionRef("room")

            version("media3", "1.0.0-beta03")
            library("exoplayer", "androidx.media3", "media3-exoplayer").versionRef("media3")

            version("ktor", "2.1.2")
            library("ktor-client-core", "io.ktor", "ktor-client-core").versionRef("ktor")
            library("ktor-client-cio", "io.ktor", "ktor-client-okhttp").versionRef("ktor")
            library("ktor-client-content-negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("ktor-client-encoding", "io.ktor", "ktor-client-encoding").versionRef("ktor")
            library("ktor-client-serialization", "io.ktor", "ktor-client-serialization").versionRef("ktor")
            library("ktor-serialization-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

            library("brotli", "org.brotli", "dec").version("0.1.2")

            library("palette", "androidx.palette", "palette").version("1.0.0")

            library("desugaring", "com.android.tools", "desugar_jdk_libs").version("1.1.5")

            library("guava", "com.google.guava", "guava").version("27.1-android")

            library("mlkitface", "com.google.android.gms", "play-services-mlkit-face-detection").version("17.1.0")

//            version("camera", "1.2.2")
            library("camera2", "androidx.camera", "camera-camera2").version("1.2.2")
            library("lifecycle", "androidx.camera", "camera-lifecycle").version("1.2.2")
            library("view", "androidx.camera", "camera-view").version("1.2.2")
            library("core", "androidx.camera", "camera-core").version("1.2.2")

            library("appcompat", "androidx.appcompat" , "appcompat").version("1.6.1")
            library("constraint", "androidx.constraintlayout" , "constraintlayout").version("2.1.4")

            library("viewmodel", "androidx.lifecycle" , "lifecycle-viewmodel-ktx").version("2.1.4")
            library("runtime", "androidx.lifecycle" , "lifecycle-runtime-ktx").version("2.1.4")

//            library("activity", "androidx.activity" , "activity-ktx").version("1.7.1")
            library("activitycompose", "androidx.activity" , "activity-compose").version("1.6.0")
            library("navigation", "androidx.navigation" , "navigation-runtime-ktx").version("2.7.6")
        }

        create("testLibs") {
            library("junit", "junit", "junit").version("4.13.2")
        }
    }
}

rootProject.name = "MoodBasedMusicApp"
include(":app")
include(":compose-routing")
include(":compose-reordering")
include(":compose-persist")
include(":innertube")
include(":ktor-client-brotli")
include(":kugou")
