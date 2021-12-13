package com.lzitech.filterme.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

class EditImageRepositoryImpl(private val context: Context) : EditImageRepository {
    override suspend fun prepareImagePreview(imageUri: Uri): Bitmap? {
        getInputStreamFromUri(imageUri).let { inputStream ->
            val originBitmap = BitmapFactory.decodeStream(inputStream)
            val width = context.resources.displayMetrics.widthPixels
            val height = ((originBitmap.height * width) / originBitmap.width)
            return Bitmap.createScaledBitmap(originBitmap, width, height, false)
        }
    }

    private fun getInputStreamFromUri(uri: Uri): InputStream? {
        return context.contentResolver.openInputStream(uri)
    }
}