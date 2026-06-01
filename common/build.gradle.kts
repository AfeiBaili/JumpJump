plugins {
    kotlin("jvm")
}

group = "cn.afeibaili"
version = "common-${properties["version"] as String}"
val wrapVersion = "0.0.1"

dependencies {
    testImplementation(kotlin("test"))

    api("cn.afeibaili.gl:wrap-gl:$wrapVersion")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}