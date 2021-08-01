package com.reviakin_package.pokemon_app.utils

import com.reviakin_package.pokemon_app.app.App
import java.io.File

class AppCacheUtil {
    companion object{
        fun getPath(): String{
            return App.applicationContext().filesDir.absolutePath + File.separator
        }
    }
}