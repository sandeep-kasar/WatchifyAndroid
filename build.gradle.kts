// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.sonarqube)
}

sonarqube {
    properties {
        property("sonar.projectKey", "your_project_key")
        property("sonar.organization", "your_org") // for SonarCloud
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.login", System.getenv("SONAR_TOKEN"))

        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.sources", "app/src/main/java")
        property("sonar.tests", listOf("app/src/test/java", "app/src/androidTest/java"))
        property("sonar.java.binaries", "app/build/intermediates/classes/debug")
        property("sonar.junit.reportPaths", listOf("app/build/test-results/testDebugUnitTest"))
        property("sonar.coverage.jacoco.xmlReportPaths", listOf("app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"))

        property("sonar.gradle.skipCompile", "true")
    }
}
