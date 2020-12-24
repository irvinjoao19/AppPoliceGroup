package com.dsige.police.group.data.module

import com.dsige.police.group.ui.broadcasts.GpsReceiver
import com.dsige.police.group.ui.broadcasts.MovilReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServicesModule {

    @ContributesAndroidInjector
    internal abstract fun provideMainReceiver(): MovilReceiver

    @ContributesAndroidInjector
    internal abstract fun provideGpsReceiver(): GpsReceiver

//    @ContributesAndroidInjector
//    internal abstract fun provideSocketReceiver(): SocketServices
}