plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")

    dependencies {
        implementation("org.apache.logging.log4j:log4j-api:2.7")
        implementation("org.apache.logging.log4j:log4j-core:2.7")
        implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.7")
        implementation("ch.qos.logback:logback-classic:1.2.6")
        runtimeOnly("org.slf4j:slf4j-reload4j:2.0.10")
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks.test {
        useJUnitPlatform()
    }
}


