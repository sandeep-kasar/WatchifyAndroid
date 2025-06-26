import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.compose)

    id("jacoco")
}

fun getLocalProperty(key: String): String? {
    val propertiesFile = rootProject.file("local.properties")
    if (propertiesFile.exists()) {
        val properties = Properties()
        properties.load(propertiesFile.inputStream())
        return properties.getProperty(key)
    }
    return null
}

val apiKey: String = getLocalProperty("API_KEY") ?: "\"MISSING_API_KEY\""

android {
    namespace = "com.sandeepk.watchify"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sandeepk.watchify"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

        buildTypes {
            debug {
                isDebuggable = true
                applicationIdSuffix = ".debug"
                versionNameSuffix = "-debug"
            }
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    kapt {
        correctErrorTypes = true
    }

    packagingOptions {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/NOTICE.md",
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/NOTICE"
            )
        }
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)

    // Networking
    implementation(libs.google.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    // UI
    implementation(libs.coil.compose)
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    testImplementation(libs.paging.common)

    // Testing - Unit
    testImplementation(libs.junit)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.room.testing)
    testImplementation(libs.truth)

    // Testing - Instrumented
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.mockk.android)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "**/di/**",
        "**/*Hilt*.*"
    )

    classDirectories.setFrom(
        files(
            fileTree("${buildDir}/intermediates/javac/debug") {
                exclude(fileFilter)
            },
            fileTree("${buildDir}/tmp/kotlin-classes/debug") {
                exclude(fileFilter)
            }
        )
    )

    sourceDirectories.setFrom(files("src/main/java", "src/main/kotlin"))

    executionData.setFrom(
        fileTree(buildDir) {
            include(
                "jacoco/testDebugUnitTest.exec",
                "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"
            )
        }
    )
}


