import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.moowork.gradle.node.npm.NpmTask

plugins {
	id("org.springframework.boot") version "2.3.7.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"

	id("com.github.node-gradle.node") version "2.2.2"
}

group = "com.kish.learn"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.apache.kafka:kafka-streams")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.apache.commons:commons-pool2")

//	runtimeOnly("com.h2database:h2")
//	runtimeOnly("mysql:mysql-connector-java")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

node{
	version = "14.4.0"
	npmVersion =  "6.14.8"
	yarnVersion= "1.22.10"
	npmInstallCommand= "install"
	distBaseUrl="https://nodejs.org/dist"
	download = true
	workDir = file("${project.buildDir}/nodejs")
	npmWorkDir = file("${project.buildDir}/npm")
	yarnWorkDir = file("${project.buildDir}/yarn")
	nodeModulesDir =file("${project.projectDir}/webapp")

}

tasks.register<NpmTask>("appNpmInstall") {
	description = "Installs all dependencies from package.json"
	workingDir = file("${project.projectDir}/webapp")
	args = listOf("install")
}

tasks.register<NpmTask>("installDep") {
	description = "Installs all dependencies from package.json"
	workingDir = file("${project.projectDir}/webapp")
	val dep:Any? = project.findProperty("dep");
	println(" dep to install ${dep?.toString()}")
	args = listOf("install","--save-dev",dep.toString())
}


tasks.register<NpmTask>("appNpmBuild") {
	println("${project.projectDir}/webapp")
	dependsOn("appNpmInstall")
	description = "Builds project"
	workingDir = file("${project.projectDir}/webapp")
	args = listOf("run", "build")
}

tasks.register<Copy>("copyWebApp") {
	dependsOn("appNpmBuild")
	description = "Copies built project to where it will be served"
	from("${project.projectDir}/webapp/build")
	into("${project.buildDir}/resources/main/static/.")
}


tasks.withType<KotlinCompile> {
	dependsOn("copyWebApp")
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}