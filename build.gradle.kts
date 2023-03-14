val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val koin_ktor_version: String by project // =3.3.1
val ktorm_version: String by project // =3.6.0
val ktor_gson_version: String by project // =1.6.8
val kodein_di_version: String by project // 7.17.0

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

group = "com.undsf.mangaldr"
version = "0.0.1"
application {
    mainClass.set("com.undsf.mangaldr.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // http-server
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")

    // websocket-server
    implementation("io.ktor:ktor-server-websockets:$ktor_version")

    // http-client
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

    // serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-gson:$ktor_version")

    // logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // dependency injection
    implementation("io.insert-koin:koin-ktor:$koin_ktor_version")
    implementation("org.kodein.di:kodein-di-jvm:7.17.0")

    // database
    implementation("org.ktorm:ktorm-core:$ktorm_version")
    implementation("io.ktor:ktor-server-websockets-jvm:2.2.4")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}