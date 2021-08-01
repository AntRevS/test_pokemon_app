package com.reviakin_package.pokemon_app.component

import com.reviakin_package.pokemon_app.app.AppScope
import com.reviakin_package.pokemon_app.module.ApplicationModule
import com.reviakin_package.pokemon_app.module.DatabaseModule
import com.reviakin_package.pokemon_app.module.PokemonApiModule
import dagger.Component

@AppScope
@Component(modules = [ApplicationModule::class, DatabaseModule::class, PokemonApiModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{
        fun applicationModule(applicationModule: ApplicationModule) : Builder
        fun build() : AppComponent
    }

    fun getViewModelComponent(): ViewModelComponent
    fun getPicassoComponent(): PicassoComponent
}