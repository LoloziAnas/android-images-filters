package com.lzitech.filterme.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.lzitech.filterme.data.ImageFilter
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.*
import java.io.InputStream

class EditImageRepositoryImpl(private val context: Context) : EditImageRepository {

    override suspend fun getImageFilters(image: Bitmap): List<ImageFilter> {
        val gpuImage = GPUImage(context).apply {
            setImage(image)
        }
        val imageFilters: ArrayList<ImageFilter> = ArrayList()
        // region:: Image Filters

        GPUImageSepiaToneFilter().also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(ImageFilter("sepia", filter, gpuImage.bitmapWithFilterApplied))
        }
        GPUImageAddBlendFilter().also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(ImageFilter("blend", filter, gpuImage.bitmapWithFilterApplied))
        }
        GPUImageBilateralBlurFilter().also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(ImageFilter("blur", filter, gpuImage.bitmapWithFilterApplied))
        }
        GPUImageBoxBlurFilter().also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(ImageFilter("box blur", filter, gpuImage.bitmapWithFilterApplied))
        }
        GPUImageBrightnessFilter().also { filter ->
            gpuImage.setFilter(filter)
            imageFilters.add(ImageFilter("brightness", filter, gpuImage.bitmapWithFilterApplied))
        }

        // endregion

        return imageFilters
    }

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