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
    mainClass.set("studying.Main")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:4.1.1")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.mockito:mockito-junit-jupiter:5.20.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.14"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    classDirectories.setFrom(files(classDirectories.files.map {
        fileTree(it) {
            exclude("studying/Main.class")
            exclude("studying/config/**")
            exclude("studying/infrastructure/ui/**")
        }
    }))
    reports {
        html.required.set(true)
        xml.required.set(true)
    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.test)
    classDirectories.setFrom(files(classDirectories.files.map {
        fileTree(it) {
            exclude("studying/Main.class")
            exclude("studying/config/**")
            exclude("studying/infrastructure/ui/**")
        }
    }))
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = "0.60".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

// Настройка Checkstyle
val sunChecks = configurations.detachedConfiguration(
    dependencies.create("com.puppycrawl.tools:checkstyle:14.1.0")
).apply {
    isTransitive = false
}

val checkstyleConfig = layout.buildDirectory.file("checkstyle/sun_checks.xml")

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
            .replace("<module name=\"FinalParameters\"/>", "") // Отключаем требование final для параметров
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
        html.required.set(true)
        xml.required.set(false)
    }
}

tasks.named("checkstyleTest") {
    enabled = false
}

tasks.named<JavaExec>("bootRun") {
    standardInput = System.`in`
}
