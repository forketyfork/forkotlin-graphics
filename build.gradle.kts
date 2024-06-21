plugins {
    kotlin("jvm") version "1.8.10"
    application
}

group = "me.forketyfork.forkotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.3") // TODO version
    testImplementation("org.assertj:assertj-core:3.24.2") // TODO extract version
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
