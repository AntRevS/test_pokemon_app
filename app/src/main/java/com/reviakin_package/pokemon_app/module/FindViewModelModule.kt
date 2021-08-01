package com.reviakin_package.pokemon_app.module

import com.reviakin_package.pokemon_app.mvvm.viewmodel.FindViewModel
import com.reviakin_package.pokemon_app.mvvm.PokemonRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FindViewModelModule {

    @Singleton
    @Provides
    fun provideFindViewModel(repository: PokemonRepository): FindViewModel {
        return FindViewModel(repository)
    }
}