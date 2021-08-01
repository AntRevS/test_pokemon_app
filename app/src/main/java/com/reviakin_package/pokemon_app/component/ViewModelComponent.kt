package com.reviakin_package.pokemon_app.component

import com.reviakin_package.pokemon_app.mvvm.viewmodel.FindViewModel
import com.reviakin_package.pokemon_app.module.FindViewModelModule
import com.reviakin_package.pokemon_app.module.RepositoryModule
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [RepositoryModule::class, FindViewModelModule::class])
interface ViewModelComponent {
    fun getFindViewModel(): FindViewModel
}