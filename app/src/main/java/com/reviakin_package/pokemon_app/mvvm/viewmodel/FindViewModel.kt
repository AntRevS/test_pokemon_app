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

class FindViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()

    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    val dataFind = repository.dataFind
    val dataExist = repository.dataCurrentExist

    fun fetchFindData(name: String){
        viewModelScope.launch {
            try{
                _loadingState.value = LoadingState.LOADING
                repository.refreshFindData(name)
                _loadingState.value = LoadingState.LOADED
            }catch (e: HttpException){
                _loadingState.value = LoadingState.error(e.stackTraceToString())
            }
        }
    }

    fun savePokemon(pokemonUnit: PokemonUnit){
        viewModelScope.launch {
            try{
                repository.addPokemonData(pokemonUnit)
            }catch (e: Exception){
            }
        }
    }

    fun deletePokemon(pokemonUnit: PokemonUnit){
        viewModelScope.launch {
            try{
                repository.deletePokemonData(pokemonUnit)
            }catch (e: Exception){

            }
        }
    }
}