import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    `maven-publish`
    signing
}

group = "com.composenative.swift"
version = project.findProperty("version") as? String ?: "1.0.0"

kotlin {
    val xcf = XCFramework("ComposeNativeCore")

    androidTarget {
        publishLibraryVariants("release", "debug")
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    
    jvm {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeNativeCore"
            isStatic = true
            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.appcompat)
        }

        iosMain.dependencies {
        }
    }
}

android {
    namespace = "com.composenative.swift.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

publishing {
    repositories {
        maven {
            name = "Sonatype"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.findProperty("ossrhUsername") as? String ?: System.getenv("OSSRH_USERNAME")
                password = project.findProperty("ossrhPassword") as? String ?: System.getenv("OSSRH_PASSWORD")
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/composenative/ComposeNativeSwift")
            credentials {
                username = project.findProperty("gpr.user") as? String ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as? String ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications.withType<MavenPublication> {
        pom {
            name.set("ComposeNativeSwift Core")
            description.set("Kotlin Multiplatform library translating Compose UI declarations into 100% genuine native SwiftUI views on iOS")
            url.set("https://github.com/composenative/ComposeNativeSwift")
            licenses {
                license {
                    name.set("The Apache License, Version 2.0")
                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                }
            }
            developers {
                developer {
                    id.set("composenative")
                    name.set("ComposeNative Contributors")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/composenative/ComposeNativeSwift.git")
                developerConnection.set("scm:git:ssh://github.com/composenative/ComposeNativeSwift.git")
                url.set("https://github.com/composenative/ComposeNativeSwift")
            }
        }
    }
}

signing {
    val signingKey = project.findProperty("signingKey") as? String ?: System.getenv("SIGNING_KEY")
    val signingPassword = project.findProperty("signingPassword") as? String ?: System.getenv("SIGNING_PASSWORD")
    if (!signingKey.isNullOrBlank()) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}
