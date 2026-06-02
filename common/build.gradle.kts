plugins {
    kotlin("jvm")
}

group = "cn.afeibaili"
version = "common-${properties["version"] as String}"

dependencies {
    testImplementation(kotlin("test"))
    api("cn.afeibaili.gl:engine2d:${properties["wrap.version"]}")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}