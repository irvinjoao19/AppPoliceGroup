package com.dsige.police.group.data.module

import com.dsige.police.group.ui.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

abstract class FragmentBindingModule {

    @Module
    abstract class Main {
        @ContributesAndroidInjector
        internal abstract fun providMainFragment(): MainFragment

    }

    @Module
    abstract class Form {
        @ContributesAndroidInjector
        internal abstract fun providGeneralFragment(): GeneralFragment

        @ContributesAndroidInjector
        internal abstract fun providFirmFragment(): FirmFragment

        @ContributesAndroidInjector
        internal abstract fun providIncidenciaFragment(): IncidenciaFragment
    }

}