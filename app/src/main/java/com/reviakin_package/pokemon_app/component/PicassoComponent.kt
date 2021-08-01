package com.reviakin_package.pokemon_app.component

import com.reviakin_package.pokemon_app.module.PicassoModule
import com.squareup.picasso.Picasso
import dagger.Subcomponent

@Subcomponent(modules = [PicassoModule::class])
interface PicassoComponent {
    fun getPicasso(): Picasso
}