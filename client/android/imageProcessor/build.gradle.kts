// Top-level build file where you can add configuration options common to all sub-projects/modules.
import java.util.Properties

// In case your system is using a proxy setup
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}
localProperties.getProperty("socksProxyHost")?.let {
    System.setProperty("socksProxyHost", it)
}

localProperties.getProperty("socksProxyPort")?.let {
    System.setProperty("socksProxyPort", it)
}
//

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}