plugins {
    java
    id("com.github.spotbugs") version "6.0.18"
    checkstyle
    pmd
}

repositories {
    mavenCentral()
}

java {
    toolchain {
    	languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation("com.mysql:mysql-connector-j:9.3.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.6")
}

tasks.test {
    useJUnitPlatform()
}

spotbugs {
    toolVersion.set("4.8.6")
    effort.set(com.github.spotbugs.snom.Effort.MAX)
    reportLevel.set(com.github.spotbugs.snom.Confidence.LOW)
}

tasks.withType<com.github.spotbugs.snom.SpotBugsTask>().configureEach {
    group = "verification"
    ignoreFailures = false

    reports {
        create("html") {
            required.set(true)
            outputLocation.set(
                layout.buildDirectory.file("reports/spotbugs/spotbugs.html").get().asFile
            )
        }
    }
}

checkstyle {
    toolVersion = "10.12.5"
    configFile = file("config/checkstyle/checkstyle.xml")
}

tasks.withType<Checkstyle>().configureEach {
    group = "verification"
    reports {
        html.required.set(true)
        xml.required.set(false)
    }
}

pmd {
    toolVersion = "6.55.0"
    isConsoleOutput = true
    ruleSets = listOf()
    ruleSetFiles = files("config/pmd/ruleset.xml")
}

tasks.withType<Pmd>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(false)
    }
}

tasks.test {
    useJUnitPlatform()
}