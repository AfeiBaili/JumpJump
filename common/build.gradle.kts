plugins {
    kotlin("jvm")
}

group = "cn.afeibaili"
version = "common-${properties["version"] as String}"

dependencies {
    testImplementation(kotlin("test"))
    if (findProject(":engine") != null) {
        println("找到本地引擎依赖，使用本地依赖")
        api(project(":engine"))
    } else {
        println("找不到本地引擎依赖，使用云端依赖")
        api("cn.afeibaili.gl:engine2d:${properties["wrap.version"]}")
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}