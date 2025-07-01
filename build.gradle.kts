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
        property("sonar.projectKey", "sandeep-kasar_WatchifyAndroid")
        property("sonar.organization", "sandeep-kasar") // optional, only for SonarCloud
        property("sonar.host.url", "https://sonarcloud.io") // or your self-hosted SonarQube URL
        property("sonar.login", System.getenv("SONAR_TOKEN"))

        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.sources", "app/src/main/java")
        property("sonar.tests", "app/src/test/java,app/src/androidTest/java")
        property("sonar.java.binaries", "app/build/intermediates/classes/debug")
        property("sonar.junit.reportPaths", "app/build/test-results/testDebugUnitTest")
        property("sonar.coverage.jacoco.xmlReportPaths", "app/build/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
    }
}
