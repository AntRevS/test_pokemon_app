package com.reviakin_package.pokemon_app.pokemonapi

import com.reviakin_package.pokemon_app.pojo.PokemonUnit
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon/{name}/")
    fun getPokemonByName(@Path("name") name: String): Deferred<PokemonUnit>
}