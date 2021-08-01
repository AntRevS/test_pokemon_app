package com.reviakin_package.pokemon_app.worker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.*
import java.net.URL


class DownloadImageWorker(context: Context, val workerParams: WorkerParameters) : Worker(context,
    workerParams
) {

    private val TAG = "downloadLog"

    companion object {
        const val INPUT_URL = "url"
        const val INPUT_FILENAME = "file_name"
    }

    override fun doWork(): Result {
        var url = workerParams.inputData.getString(INPUT_URL)
        var fileName = workerParams.inputData.getString(INPUT_FILENAME)

        var icon: Bitmap? = null
        try{
            var input = URL(url).openStream()
            icon = BitmapFactory.decodeStream(input)
        }catch (e: IOException){
            return Result.failure()
        }

        try{
            var file = File(fileName)
            var out = FileOutputStream(file)
            icon.compress(Bitmap.CompressFormat.PNG, 85, out)
            Log.d(TAG, "Picture saved to: $fileName")
            out.close()
        }catch (e: FileNotFoundException){
            Log.d(TAG, e.toString())
            return Result.failure()
        }catch (e: IOException){
            Log.d(TAG, e.toString())
            return Result.failure()
        }
        return Result.success()
    }

}