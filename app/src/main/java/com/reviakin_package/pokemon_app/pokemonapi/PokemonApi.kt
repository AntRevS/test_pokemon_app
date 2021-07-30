package com.reviakin_package.pokemon_app.pokemonapi

import com.reviakin_package.pokemon_app.pojo.PokemonUnit
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/{name}/")
    fun getPokemonByName(@Path("name") name: String): Single<PokemonUnit>
}