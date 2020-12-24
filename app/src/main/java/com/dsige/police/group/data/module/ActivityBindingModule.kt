package com.dsige.police.group.data.module

import com.dsige.police.group.ui.activities.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Main::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBindingModule.Form::class])
    internal abstract fun bindFormActivity(): FormActivity

    @ContributesAndroidInjector
    internal abstract fun bindFirmActivity(): FirmActivity

    @ContributesAndroidInjector
    internal abstract fun bindPreviewCameraActivity(): PreviewCameraActivity
}