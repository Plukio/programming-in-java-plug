pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.21"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "programming-in-java"
include("m1-java-examples")
include("m2-java-practice")
include("m3-spring-application")
