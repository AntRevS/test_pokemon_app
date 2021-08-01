package com.reviakin_package.pokemon_app.module

import android.content.Context
import com.reviakin_package.pokemon_app.app.App
import com.reviakin_package.pokemon_app.app.AppScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(var app: App) {

    @AppScope
    @Provides
    fun provideApp() = app

    @AppScope
    @Provides
    fun provideContext(): Context = app.applicationContext

}