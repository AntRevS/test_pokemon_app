package com.reviakin_package.pokemon_app.module

import com.reviakin_package.pokemon_app.app.AppScope
import com.reviakin_package.pokemon_app.mvvm.PokemonRepository
import com.reviakin_package.pokemon_app.mvvm.viewmodel.FindViewModel
import com.reviakin_package.pokemon_app.mvvm.viewmodel.RandomFindViewModel
import dagger.Module
import dagger.Provides

@Module
class RandomFindViewModelModule {

    @AppScope
    @Provides
    fun provideRandomFindViewModel(repository: PokemonRepository): RandomFindViewModel {
        return RandomFindViewModel(repository)
    }
}