package com.lzitech.filterme.listeners

import com.lzitech.filterme.data.ImageFilter

interface ImageFiltersListener {
    fun onFilterSelected(filterImage: ImageFilter)
}