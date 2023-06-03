package com.bimboilya.bluepoop.di

import com.juul.kable.Scanner
import com.juul.kable.logs.Logging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ScanModule {

  @Provides
  fun scanner(): Scanner =
    Scanner {
      logging {
        level = Logging.Level.Data
        identifier = "_bluepoop-app_"
      }
    }
}
