package com.reviakin_package.pokemon_app

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File

class DeleteImageWorker(context: Context, val workerParams: WorkerParameters) : Worker(context,
    workerParams
) {

    private val TAG = "deleteImageTag"

    companion object{
        const val INPUT_PATH = "path"
    }

    override fun doWork(): Result {
        var path = workerParams.inputData.getString(INPUT_PATH)
        var file = File(path)
        if(file.exists()){
            file.delete()
            Log.d(TAG, "File deleted: $path")
            return Result.success()
        }
        return Result.failure()
    }
}