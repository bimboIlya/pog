package com.bimboilya.navsample.di.voyager

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.bimboilya.navsample.voyager.flow.SecondScreenModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
interface ScreenModelModule {

    @Binds
    @IntoMap
    @ScreenModelKey(SecondScreenModel::class)
    fun bindSecondScreenModel(screenModel: SecondScreenModel): ScreenModel
}