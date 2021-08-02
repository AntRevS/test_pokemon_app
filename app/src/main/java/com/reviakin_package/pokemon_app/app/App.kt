package com.reviakin_package.pokemon_app.app

import android.app.Application
import com.reviakin_package.pokemon_app.component.AppComponent
import com.reviakin_package.pokemon_app.component.DaggerAppComponent
import com.reviakin_package.pokemon_app.module.ApplicationModule

class App : Application() {

    lateinit var appComponent: AppComponent

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext() : App {
            return instance as App
        }
    }



    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().applicationModule(ApplicationModule(applicationContext())).build()
    }
}