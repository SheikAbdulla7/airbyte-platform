plugins {
  id("io.airbyte.gradle.jvm.lib")
  id("io.airbyte.gradle.publish")
  `java-test-fixtures`
}

configurations.all {
  exclude(group = "io.micronaut.flyway")
}

dependencies {
  compileOnly(libs.lombok)
  annotationProcessor(libs.lombok)     // Lombok must be added BEFORE Micronaut
  annotationProcessor(libs.bundles.micronaut.annotation.processor)

  ksp(platform(libs.micronaut.platform))
  ksp(libs.bundles.micronaut.annotation.processor)

  api(libs.bundles.micronaut.annotation)

  implementation(project(":airbyte-commons"))
  implementation(project(":airbyte-commons-protocol"))
  implementation(project(":airbyte-config:config-models"))
  implementation(project(":airbyte-config:specs"))
  implementation(project(":airbyte-data"))
  implementation(project(":airbyte-db:db-lib"))
  implementation(project(":airbyte-db:jooq"))
  implementation(project(":airbyte-featureflag"))
  implementation(project(":airbyte-json-validation"))
  implementation(libs.airbyte.protocol)
  implementation(project(":airbyte-metrics:metrics-lib"))
  implementation(libs.bundles.apache)
  implementation(libs.google.cloud.storage)
  implementation(libs.commons.io)
  implementation(libs.jackson.databind)
  implementation(libs.bundles.micronaut.data.jdbc)
  implementation(libs.bundles.micronaut.kotlin)
  implementation(libs.bundles.datadog)

  testImplementation(libs.hamcrest.all)
  testImplementation(libs.platform.testcontainers.postgresql)
  testImplementation(libs.bundles.flyway)
  testImplementation(libs.mockito.inline)
  testImplementation(project(":airbyte-test-utils"))
  testImplementation(libs.bundles.junit)
  testImplementation(libs.assertj.core)
  testImplementation(libs.junit.pioneer)
  testImplementation(libs.bundles.micronaut.test)
  testImplementation(libs.mockk)

  kspTest(platform(libs.micronaut.platform))
  kspTest(libs.bundles.micronaut.test.annotation.processor)

  testRuntimeOnly(libs.junit.jupiter.engine)

  integrationTestImplementation(project(":airbyte-config:config-persistence"))

  testFixturesApi(libs.jackson.databind)
  testFixturesApi(libs.guava)
  testFixturesApi(project(":airbyte-json-validation"))
  testFixturesApi(project(":airbyte-commons"))
  testFixturesApi(project(":airbyte-config:config-models"))
  testFixturesApi(project(":airbyte-config:config-secrets"))
  testFixturesApi(libs.airbyte.protocol)
  testFixturesApi(libs.lombok)
  testFixturesAnnotationProcessor(libs.lombok)
}

// The DuplicatesStrategy will be required while this module is mixture of kotlin and java _with_ lombok dependencies.
// By default, runs all annotation(processors and disables annotation(processing by javac, however).  Once lombok has
// been removed, this can also be removed.
tasks.withType<Jar>().configureEach {
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
