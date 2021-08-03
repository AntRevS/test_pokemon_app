package com.reviakin_package.pokemon_app.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reviakin_package.pokemon_app.database.entity.PokemonEntity
import com.reviakin_package.pokemon_app.helper.LoadingState
import com.reviakin_package.pokemon_app.mvvm.PokemonRepository
import com.reviakin_package.pokemon_app.pojo.PokemonUnit
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RandomFindViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()

    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    val dataFind = repository.dataFind
    val dataExist = repository.dataCurrentExist

    fun fetchFindData(){
        viewModelScope.launch {
            try{
                _loadingState.value = LoadingState.LOADING
                repository.refreshRandomFindData()
                _loadingState.value = LoadingState.LOADED
            }catch (e: HttpException){
                _loadingState.value = LoadingState.error(e.stackTraceToString())
            }
        }
    }

    fun savePokemon(pokemonUnit: PokemonUnit){
        viewModelScope.launch {
            try{
                repository.addPokemonDataUnit(pokemonUnit)
            }catch (e: Exception){
            }
        }
    }

    fun deletePokemon(name: String){
        viewModelScope.launch {
            try{
                repository.deletePokemonData(name)
            }catch (e: Exception){

            }
        }
    }
}