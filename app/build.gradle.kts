plugins {
    alias(libs.plugins.android.application) // Plugin para aplicações Android
}

android {
    namespace = "com.example.brickbreaker2" // Namespace do aplicativo
    compileSdk = 34 // Versão do SDK de compilação

    defaultConfig {
        applicationId = "com.example.brickbreaker2" // ID do aplicativo
        minSdk = 24 // Versão mínima do SDK suportada
        targetSdk = 34 // Versão do SDK alvo
        versionCode = 1 // Código da versão
        versionName = "1.0" // Nome da versão

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Runner para testes
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Não habilitar minificação para o release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro" // Arquivo de regras do ProGuard
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Compatibilidade de versão do Java
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat) // Biblioteca de compatibilidade
    implementation(libs.material) // Biblioteca Material Components
    implementation(libs.activity) // Biblioteca de atividades
    implementation(libs.constraintlayout) // Biblioteca de layout de restrição
    testImplementation(libs.junit) // Biblioteca para testes unitários
    androidTestImplementation(libs.ext.junit) // Biblioteca para testes instrumentados
    androidTestImplementation(libs.espresso.core) // Biblioteca para testes de UI
}
