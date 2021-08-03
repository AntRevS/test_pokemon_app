package com.reviakin_package.pokemon_app.module

import com.reviakin_package.pokemon_app.app.AppScope
import com.reviakin_package.pokemon_app.mvvm.PokemonRepository
import com.reviakin_package.pokemon_app.mvvm.viewmodel.RandomFindViewModel
import com.reviakin_package.pokemon_app.mvvm.viewmodel.SavedViewModel
import dagger.Module
import dagger.Provides

@Module
class SavedViewModelModule {

    @AppScope
    @Provides
    fun provideSavedViewModel(repository: PokemonRepository): SavedViewModel {
        return SavedViewModel(repository)
    }
}