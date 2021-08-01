package com.reviakin_package.pokemon_app.module

import android.content.Context
import com.reviakin_package.pokemon_app.app.AppScope
import com.reviakin_package.pokemon_app.database.AppDataBase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @AppScope
    @Provides
    fun provideAppDatabase(context : Context): AppDataBase{
        return AppDataBase.buildDatabase(context)
    }
}