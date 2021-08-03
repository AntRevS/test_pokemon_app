package com.reviakin_package.pokemon_app.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reviakin_package.pokemon_app.database.entity.PokemonEntity
import io.reactivex.Completable

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemonEntity: PokemonEntity)

    @Query("DELETE FROM PokemonEntity WHERE name = :name")
    fun deleteByName(name: String)

    @Query("SELECT * FROM PokemonEntity WHERE id != 100000")
    fun getAllRows(): LiveData<List<PokemonEntity>>

    @Query("SELECT EXISTS(SELECT name FROM PokemonEntity WHERE name = :name)")
    fun checkExistByName(name: String): Boolean

    @Query("SELECT * FROM PokemonEntity WHERE id = :id")
    fun getById(id: Int) : LiveData<PokemonEntity>

    @Query("DELETE FROM POKEMONENTITY WHERE id = :id")
    fun deleteById(id: Int)
}