package com.reviakin_package.pokemon_app.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reviakin_package.pokemon_app.helper.LoadingState
import com.reviakin_package.pokemon_app.mvvm.PokemonRepository
import com.reviakin_package.pokemon_app.pojo.PokemonUnit
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SavedViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    val dataSave = repository.dataSave

    fun deletePokemon(name: String){
        viewModelScope.launch {
            try{
                repository.deletePokemonData(name)
            }catch (e: Exception){

            }
        }
    }
}