plugins {
	application
	
    `java-library`
    `java-library-distribution`
	
     id("org.openjfx.javafxplugin") version "0.0.8"
}

repositories {
    mavenCentral()
}

dependencies {	
	implementation(project(":apcf"))
	
	implementation("org.apache.logging.log4j:log4j-core:2.13.0")
	implementation("org.apache.logging.log4j:log4j-jcl:2.13.0")

	implementation("com.beust:jcommander:1.78")
	
	implementation("org.yaml:snakeyaml:1.25")
}

javafx {
    version = "11"
    modules("javafx.controls", "javafx.fxml")
}

java {
    group = "de.acagamics"
	version = "1.2.4"

    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    
    withJavadocJar()
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "de.acagamics.crushingrocks.Main"
    }
}

application {
	mainClassName = "de.acagamics.crushingrocks.Main"
}
