plugins {
    kotlin("jvm") version "2.2.20"
}

group = "cn.afeibaili"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/afeibaili/engine2d")
            credentials {
                username = System.getenv("GITHUB_ACCOUNT")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}