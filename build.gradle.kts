plugins {
    // Java-Projekt
    java

    // JaCoCo für Testabdeckung
    jacoco
}

group = "de.tasktracker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.4.1.jre11")

}

// ===== Tests =====
tasks.test {
    useJUnitPlatform()
}

// ===== JaCoCo-Konfiguration =====
jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    // Tests müssen vor dem Report laufen
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)   // XML-Report (für CI/Analyse)
        html.required.set(true)  // HTML-Report (im Browser anschauen)
        csv.required.set(false)
    }
}

// dafür sorgen, dass beim "build" auch der Coverage-Report erzeugt wird
tasks.check {
    dependsOn(tasks.jacocoTestReport)
}
