package com.reviakin_package.pokemon_app.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    var name: String,
    var icon: String,
    var baseExperience: Int,
    var height: Int,
    var weight: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}