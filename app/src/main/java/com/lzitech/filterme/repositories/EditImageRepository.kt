package com.lzitech.filterme.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.lzitech.filterme.data.ImageFilter

interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri: Uri): Bitmap?
    suspend fun getImageFilters(image: Bitmap): List<ImageFilter>
    suspend fun saveFilteredImage(filteredImage: Bitmap): Uri?
}