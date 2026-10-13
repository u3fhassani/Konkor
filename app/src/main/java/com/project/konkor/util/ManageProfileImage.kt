package com.project.konkor.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.project.konkor.model.InternalStoragePhotoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ManageProfileImage(private val context: Context) {



    fun savePhotoToInternalStorage(fileName: String, bitmap: Bitmap): Boolean {
        return try {
            context.openFileOutput("$fileName.jpg", Context.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            Log.e("saveImage", "savePhotoToInternalStorage: ", e)
            false
        }
    }

    suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhotoModel> {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhotoModel(it.name, bitmap)
            }
        } ?: emptyList()
    }

    fun deletePhotoFromInternalStorage(fileName: String): Boolean {
        return try {
            context.deleteFile(fileName)
        } catch (e: Exception) {
            Log.e("deleteImage", "deletePhotoFromInternalStorage: ", e)
            false
        }

    }

}