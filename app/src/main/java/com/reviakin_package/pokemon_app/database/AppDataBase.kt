package com.reviakin_package.pokemon_app.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.reviakin_package.pokemon_app.database.dao.PokemonDao
import com.reviakin_package.pokemon_app.database.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pokemonDao() : PokemonDao

    companion object{
        fun buildDatabase(app: Context) =
            Room.databaseBuilder(
                app,
                AppDataBase::class.java,
                "database"
            ).build()

    }


}