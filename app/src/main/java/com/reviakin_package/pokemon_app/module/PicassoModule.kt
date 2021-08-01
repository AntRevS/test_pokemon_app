package com.reviakin_package.pokemon_app.module

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class PicassoModule {

    @Provides
    fun providePicasso(): Picasso{
        return Picasso.get()
    }
}