plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.serialization") version "2.3.0"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "9.2.1"
    }
}
dependencies {
    implementation(kotlin("test"))
    implementation("org.junit.jupiter:junit-jupiter:5.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}