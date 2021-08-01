package com.reviakin_package.pokemon_app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.reviakin_package.pokemon_app.database.entity.PokemonEntity
import io.reactivex.Completable

@Dao
interface PokemonDao {

    @Insert
    fun insert(pokemonEntity: PokemonEntity)

    @Query("DELETE FROM PokemonEntity WHERE name = :name")
    fun deleteByName(name: String)

    @Query("SELECT * FROM PokemonEntity")
    fun getAllRows(): LiveData<List<PokemonEntity>>

    @Query("SELECT EXISTS(SELECT name FROM PokemonEntity WHERE name = :name)")
    fun checkExistByName(name: String): Boolean

}