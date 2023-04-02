plugins {
  id("pog.android.library")
  id("pog.android.compose")
}

android {
  namespace = "com.bimboilya.common.ui.components"
}

dependencies {

  implementation(libraries.compose.ui)
  debugImplementation(libraries.compose.ui.tooling)
  implementation(libraries.compose.material)
}
