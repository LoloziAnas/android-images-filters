package com.lzitech.filterme.listeners

import java.io.File

interface SavedImagesListener {
    fun onImageClick(file: File)
    fun onDeleteImageClick(file: File)
}