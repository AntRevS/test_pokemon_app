package com.reviakin_package.pokemon_app.adapter

import com.reviakin_package.pokemon_app.database.entity.PokemonEntity

class PokemonRecycler {

    interface onLikeCLickListener{
        fun likeClick(pokemonEntity: PokemonEntity)
    }

}