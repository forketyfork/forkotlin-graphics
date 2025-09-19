plugins {
    kotlin("jvm") version "2.2.20"
    application
}

group = "me.forketyfork.forkotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.13.4") // TODO version
    testImplementation("org.assertj:assertj-core:3.27.5") // TODO extract version
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
