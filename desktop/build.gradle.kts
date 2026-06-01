plugins {
    kotlin("jvm")
}

group = "cn.afeibaili"
version = "desktop-${properties["version"] as String}"

dependencies {
    testImplementation(kotlin("test"))

    implementation(project(":common"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}