package com.reviakin_package.pokemon_app.component

import com.reviakin_package.pokemon_app.module.PokemonApiModule
import com.reviakin_package.pokemon_app.pokemonapi.PokemonApi
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [PokemonApiModule::class])
interface ApiComponent {
    fun getPokemonApi(): PokemonApi
}