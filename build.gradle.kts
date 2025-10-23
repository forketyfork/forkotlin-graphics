plugins {
    kotlin("jvm") version "2.2.21"
    application
}

group = "me.forketyfork.forkotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:6.0.0") // TODO version
    testImplementation("org.assertj:assertj-core:3.27.6") // TODO extract version
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
