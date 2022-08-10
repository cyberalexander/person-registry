import org.jetbrains.kotlin.backend.common.serialization.mangle.collectForMangler

/*
 * MIT License
 *
 * Copyright (c) 2015-2021 Aliaksandr Leanovich
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
description = "Person entity managing console application."
group = "by.academy.it"
version = "3.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

val licenseName: String by project
val licenseUrl: String by project
val licenseDistribution: String by project

println(">>> Configuration phase.\nProcessing 'build.gradle.kts'.\nProject description: $description")

println(
    "\nLicense:" +
            "\nname: $licenseName" +
            "\nurl: $licenseUrl" +
            "\ndistribution: $licenseDistribution"
)

println(
    "\nContacts:" +
            "\nname: Aliaksandr Leanovich" +
            "\nemail: taurum13@gmail.com" +
            "\norganization: by.academy.it" +
            "\norganization url: https://github.com/cyberalexander/person-registry"
)

plugins {
    base
    java
    pmd
    checkstyle
    jacoco
    kotlin("jvm") version "1.6.21"
    `maven-publish`
}

/**
 * PMD plugin extension configuration.
 * @link https://docs.gradle.org/current/userguide/pmd_plugin.html
 */
pmd {
    isConsoleOutput = true
    toolVersion = project.property("pmd_plugin_version").toString()
    rulesMinimumPriority.set(3)
    ruleSetConfig = resources.text.fromFile(project.property("pmd_rules_location").toString())
    ruleSets = listOf() // it needs to clean up the default list of ruleSets configured in gradle pmd plugin and use custom one (see row above)
}

/**
 * Checkstyle plugin extension configuration.
 * @link https://docs.gradle.org/current/userguide/checkstyle_plugin.html
 */
checkstyle {
    toolVersion = "10.2"
    config = resources.text.fromFile("$rootDir/quality/checkstyle/checkstyle.xml", "UTF-8")
    configDirectory.set(file("$rootDir/quality/checkstyle"))
    isShowViolations = true
    isIgnoreFailures = false
}

/**
 * JaCoCo plugin extension configuration.
 * <a href="https://docs.gradle.org/current/userguide/jacoco_plugin.html">Jacoco Plugin</a>
 */
jacoco {
    toolVersion = "0.8.8"
    reportsDirectory.set(layout.buildDirectory.dir("reports/jacoco"))
}

repositories {
    flatDir {
        dir("$rootDir/libs/libraries")
    }
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("org.apache.logging.log4j:log4j-api:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.hibernate:hibernate-core:5.6.8.Final")
    implementation("org.hibernate:hibernate-entitymanager:5.6.8.Final")
    implementation("mysql:mysql-connector-java:8.0.29")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.6.1")
    testImplementation("org.mockito:mockito-core:4.6.1")

    compileOnly("org.projectlombok:lombok:1.18.24")
    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    //local dependencies
    compileOnly(fileTree("$rootDir/dependencies") { include("*.jar") })
    runtimeOnly(fileTree("$rootDir/dependencies") { include("*.jar") })
    implementation(files("$rootDir/dependencies/winter-io-2022.04.01-SNAPSHOT-jar-with-dependencies.jar"))
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    build {
        dependsOn(withType<Jar>())
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    register<Copy>("copyDependencies") {
        println(">>> Register copyDependencies task")
        from(configurations.compileClasspath)
        into("$buildDir/libs/libraries")
        doLast {
            println(">>> Execute copyDependencies task")
        }
    }

    withType<Jar> {
        println(">>> Execute Jar task")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes["Manifest-Version"] = "1.0"
            attributes["Main-Class"] = "by.academy.it.PersonRegistryApplication"
        }
        configurations["compileClasspath"].forEach { file: File ->
            from(zipTree(file.absoluteFile))
        }
    }

    withType<Test> {
        dependsOn(withType<Copy>())
        useJUnitPlatform()

        val events = project.findProperty("testLoggingEvents") as String? ?: "PASSED,FAILED,SKIPPED"
        val testLoggingEvents = events.split(",")
            .map { org.gradle.api.tasks.testing.logging.TestLogEvent.valueOf(it) }
            .toTypedArray()

        testLogging {
            events(*testLoggingEvents)
            showStandardStreams = false
        }

        configure<JacocoTaskExtension> {
            //TODO configure
        }

        finalizedBy(jacocoTestReport) // report is always generated after tests run
    }

    jacocoTestReport {
        dependsOn(test) // tests are required to run before generating the report

        reports {
            xml.required.set(false)
            csv.required.set(false)
            html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/html"))
        }
        finalizedBy(jacocoTestCoverageVerification)
    }

    /**
     * Jacoco rules can be applied to the following element types with a list of limits:
     * BUNDLE - all classes in the module combined will be checked against the rule.
     * PACKAGE - each java package.
     * CLASS - each Class file will be checked against the rule.
     * SOURCEFILE - each Java file will be checked against the rule. 1 java file can contain multiple classes.
     * METHOD
     *
     * The limits apply to the following counters
     * INSTRUCTION
     * LINE
     * BRANCH
     * COMPLEXITY
     * METHOD
     * CLASS
     *
     * A minimum or maximum for the following can be defined
     * TOTALCOUNT
     * COVEREDCOUNT
     * MISSEDCOUNT
     * COVEREDRATIO
     * MISSEDRATIO
     *
     * Sources:
     * 1) <a href="https://rhamedy.medium.com/how-to-setup-jacoco-code-coverage-with-maven-gradle-76e0b2fca9fb">source 1</a>
     */
    jacocoTestCoverageVerification {
        dependsOn(jacocoTestReport)

        // exclusions from jacoco analysis
        classDirectories.setFrom(
            files(classDirectories.files.map {
                fileTree(it) {
                    exclude(
                        "by/academy/it/*PersonRegistryApplication.*",
                        "by/academy/it/exception/**",
                        "by/academy/it/domain/**",
                        "by/academy/it/menu/**",
                        "by/academy/it/util/**",
                        "$buildDir/resources",
                        "*.jar"
                    )
                }
            })
        )

        violationRules {
            rule {
                limit {
                    minimum = "0.55".toBigDecimal()
                }

                limit {
                    counter = "CLASS"
                    value = "MISSEDCOUNT"
                    maximum = "2".toBigDecimal()
                }
            }

            rule {
                isEnabled = true
                element = "CLASS"
                includes = listOf("org.gradle.*")

                limit {
                    counter = "LINE"
                    value = "TOTALCOUNT"
                    maximum = "0.5".toBigDecimal()
                }

                limit {
                    counter = "LINE"
                    value = "COVEREDRATIO"
                    maximum = "0.5".toBigDecimal()
                }

                limit {
                    counter = "INSTRUCTION"
                    value = "COVEREDRATIO"
                    maximum = "0.5".toBigDecimal()
                }

                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    maximum = "0.3".toBigDecimal()
                }

                limit {
                    counter = "COMPLEXITY"
                    value = "COVEREDRATIO"
                    maximum = "0.55".toBigDecimal()
                }

                limit {
                    counter = "METHOD"
                    value = "COVEREDRATIO"
                    maximum = "0.5".toBigDecimal()
                }
            }
        }
    }
}