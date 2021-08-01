package com.reviakin_package.pokemon_app.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.reviakin_package.pokemon_app.app.AppScope
import com.reviakin_package.pokemon_app.pokemonapi.PokemonApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class PokemonApiModule {

    @AppScope
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build()
    }

    @AppScope
    @Provides
    fun providePokemonApi(retrofit: Retrofit): PokemonApi{
        return retrofit.create(PokemonApi::class.java)
    }
}