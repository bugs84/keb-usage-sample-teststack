plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.61"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

repositories {
    jcenter()
}

val kebVersion = "0.3"
dependencies {
    testImplementation("org.kebish:keb-core:$kebVersion")
    testImplementation("org.kebish:keb-junit5:$kebVersion")
    testImplementation("org.kebish:keb-bobril:$kebVersion")


    // For downloading correct WebDriver we recommend to use WebDriverManager
    // https://github.com/bonigarcia/webdrivermanager
    testImplementation("io.github.bonigarcia:webdrivermanager:3.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")
    testImplementation("org.assertj:assertj-core:3.11.1")


    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
