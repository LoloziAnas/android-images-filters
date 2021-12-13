package com.lzitech.filterme.repositories

import android.graphics.Bitmap
import android.net.Uri

interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri: Uri): Bitmap?
}