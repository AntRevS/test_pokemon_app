package com.reviakin_package.pokemon_app.pojo

import com.google.gson.annotations.SerializedName

data class PokemonUnit(
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("sprites") var sprites : Sprites,

)