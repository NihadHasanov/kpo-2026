plugins {
    application
    checkstyle
    java
    jacoco
    id("org.springframework.boot")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass = "studying.Main"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:4.1.1")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.mockito:mockito-junit-jupiter:5.20.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:4.1.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
}

val sunChecks = configurations.detachedConfiguration(
    dependencies.create("com.puppycrawl.tools:checkstyle:14.1.0")
).apply {
    isTransitive = false
}

val checkstyleConfig = layout.buildDirectory.file(
    "checkstyle/sun_checks.xml"
)

val prepareCheckstyleConfig = tasks.register("prepareCheckstyleConfig") {
    inputs.files(sunChecks)
    outputs.file(checkstyleConfig)
    doLast {
        val standardConfig = resources.text
            .fromArchiveEntry(sunChecks, "sun_checks.xml")
            .asString()
        val relaxed = standardConfig
            .replace("<module name=\"JavadocPackage\"/>", "")
            .replace("<module name=\"JavadocVariable\"/>", "")
            .replace(
                "<module name=\"TreeWalker\">",
                "<module name=\"SuppressWarningsFilter\"/>\n"
                        + "<module name=\"TreeWalker\">\n"
                        + "<module name=\"SuppressWarningsHolder\"/>"
            )
        checkstyleConfig.get().asFile.apply {
            parentFile.mkdirs()
            writeText(relaxed)
        }
    }
}

checkstyle {
    toolVersion = "14.1.0"
    config = resources.text.fromFile(checkstyleConfig.get().asFile)
    isShowViolations = true
    isIgnoreFailures = false
    maxWarnings = 0
}

tasks.withType<Checkstyle>().configureEach {
    dependsOn(prepareCheckstyleConfig)
    reports {
        html.required = true
        xml.required = false
    }
}
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
