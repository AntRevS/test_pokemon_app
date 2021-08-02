package com.reviakin_package.pokemon_app.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.reviakin_package.pokemon_app.worker.DeleteImageWorker
import com.reviakin_package.pokemon_app.worker.DownloadImageWorker
import com.reviakin_package.pokemon_app.database.dao.PokemonDao
import com.reviakin_package.pokemon_app.database.entity.PokemonEntity
import com.reviakin_package.pokemon_app.pojo.PokemonUnit
import com.reviakin_package.pokemon_app.pokemonapi.PokemonApi
import com.reviakin_package.pokemon_app.utils.AppCacheUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class PokemonRepository(
    private val pokemonDao: PokemonDao,
    private val pokemonApi: PokemonApi
) {

    val dataSave = pokemonDao.getAllRows()
    val dataFind = MutableLiveData<PokemonUnit>()
    val dataRandom = MutableLiveData<PokemonUnit>()

    val dataCurrentExist = MutableLiveData<Boolean>()

    suspend fun refreshFindData(name : String) = coroutineScope {
        withContext(Dispatchers.IO){
            var pokemon = pokemonApi.getPokemonByName(name).await()
            dataFind.postValue(pokemon)

            checkExistPokemonData(pokemon.name)
        }
    }

    suspend fun addPokemonData(pokemonUnit: PokemonUnit) = coroutineScope {
        withContext(Dispatchers.IO){
            var path = AppCacheUtil.getPath() + pokemonUnit.name + ".png"
            var requestData = Data.Builder()
                .putString(DownloadImageWorker.INPUT_URL, pokemonUnit.sprites.frontDefault)
                .putString(DownloadImageWorker.INPUT_FILENAME, path)
                .build()
            var downloadImageRequest = OneTimeWorkRequest.Builder(DownloadImageWorker::class.java)
                .setInputData(requestData)
                .build()
            WorkManager.getInstance().enqueue(downloadImageRequest)

            pokemonDao.insert(
                PokemonEntity(
                    pokemonUnit.name,
                    path,
                    pokemonUnit.baseExperience,
                    pokemonUnit.height,
                    pokemonUnit.weight
                )
            )

            checkExistPokemonData(pokemonUnit.name)
        }
    }

    suspend fun deletePokemonData(name: String) = coroutineScope {
        withContext(Dispatchers.IO){
            var path = AppCacheUtil.getPath() + name + ".png"
            var requestData = Data.Builder()
                .putString(DeleteImageWorker.INPUT_PATH, path)
                .build()
            var deleteImageRequest = OneTimeWorkRequest.Builder(DeleteImageWorker::class.java)
                .setInputData(requestData)
                .build()
            WorkManager.getInstance().enqueue(deleteImageRequest)

            pokemonDao.deleteByName(name)

            checkExistPokemonData(name)
        }
    }

    private suspend fun checkExistPokemonData(name: String) = coroutineScope {
        withContext(Dispatchers.IO){
            dataCurrentExist.postValue(pokemonDao.checkExistByName(name))
        }
    }

}