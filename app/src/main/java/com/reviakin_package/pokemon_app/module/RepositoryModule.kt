package com.reviakin_package.pokemon_app.module

import com.reviakin_package.pokemon_app.app.AppScope
import com.reviakin_package.pokemon_app.database.AppDataBase
import com.reviakin_package.pokemon_app.mvvm.PokemonRepository
import com.reviakin_package.pokemon_app.pokemonapi.PokemonApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(appDataBase: AppDataBase, pokemonApi: PokemonApi): PokemonRepository{
        return PokemonRepository(appDataBase.pokemonDao(), pokemonApi)
    }

}