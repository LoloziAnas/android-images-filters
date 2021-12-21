package com.lzitech.filterme.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File

class SavedImageRepositoryImpl(private val context: Context) : SavedImageRepository {

    override suspend fun loadSavedImages(): List<Pair<File, Bitmap>>? {
        // create arraylist for storing the saved images in the directory
        val savedImages = ArrayList<Pair<File, Bitmap>>()
        // navigate to the saved images folder
        val dir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Saved Images")
        // listing the saved images and add them to arraylist
        dir.listFiles()?.let { data ->
            data.forEach { file ->
                savedImages.add(Pair(file, loadPreviewBitmap(file)))
            }
            return savedImages
        } ?: return null
    }

    private fun loadPreviewBitmap(file: File): Bitmap {
        val originalBitmap = BitmapFactory.decodeFile(file.absolutePath)
        val width = 150
        val height = ((originalBitmap.height * width) / originalBitmap.width)
        return Bitmap.createScaledBitmap(originalBitmap, width, height, false)
    }
}