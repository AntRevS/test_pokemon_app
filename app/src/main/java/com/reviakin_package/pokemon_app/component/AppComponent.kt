package com.reviakin_package.pokemon_app.component

import com.reviakin_package.pokemon_app.app.AppScope
import com.reviakin_package.pokemon_app.module.*
import com.reviakin_package.pokemon_app.mvvm.viewmodel.FindViewModel
import com.reviakin_package.pokemon_app.mvvm.viewmodel.RandomFindViewModel
import com.reviakin_package.pokemon_app.mvvm.viewmodel.SavedViewModel
import dagger.Component

@AppScope
@Component(modules = [
    ApplicationModule::class,
    DatabaseModule::class,
    PokemonApiModule::class,
    RepositoryModule::class,
    FindViewModelModule::class,
    RandomFindViewModelModule::class,
    SavedViewModelModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{
        fun applicationModule(applicationModule: ApplicationModule) : Builder
        fun build() : AppComponent
    }

    fun getFindViewModel(): FindViewModel
    fun getRandomFindViewModel(): RandomFindViewModel
    fun getSavedViewModel(): SavedViewModel
    fun getPicassoComponent(): PicassoComponent
}