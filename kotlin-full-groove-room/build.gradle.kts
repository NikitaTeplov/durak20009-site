plugins {
    kotlin("jvm") version "2.0.21"
    application
    id("io.ktor.plugin") version "3.0.3"
}

group = "com.nikitateplov.groove"
version = "1.0.0"

val ktorVersion = "3.0.3"

application {
    mainClass.set("com.nikitateplov.groove.ApplicationKt")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-html-builder-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.5.12")
}
