plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.7")
}

tasks.test {
    useJUnitPlatform()
}


tasks.register("printMassage") {
    group = "myTask"
    doLast {
        println("Test run is over")
    }
}

tasks.register<Test>("runTest") {
    group = "myTask"
    useJUnitPlatform {
        includeTags("Test")
    }
    testLogging {
        showStandardStreams = true
    }
}

tasks.named("printMassage") {
    dependsOn("runTest")
    dependsOn("clean")
}