package com.reviakin_package.pokemon_app.component

import com.reviakin_package.pokemon_app.app.AppScope
import dagger.Component

@AppScope
@Component
interface AppComponent {
    fun getApiComponent(): ApiComponent
}