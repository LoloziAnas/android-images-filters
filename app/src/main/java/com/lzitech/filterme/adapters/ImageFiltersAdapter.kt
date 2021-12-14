package com.lzitech.filterme.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzitech.filterme.data.ImageFilter
import com.lzitech.filterme.databinding.ItemContainerFiltersBinding

class ImageFiltersAdapter(private val imageFilters: List<ImageFilter>) :
    RecyclerView.Adapter<ImageFiltersAdapter.ImageFiltersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFiltersViewHolder {
        val binding =
            ItemContainerFiltersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageFiltersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageFiltersViewHolder, position: Int) {
        with(holder) {
            with(imageFilters[position]) {
                binding.imageFilterPreview.setImageBitmap(filterPreview)
                binding.textFilterName.text = name
            }
        }
    }

    override fun getItemCount() = imageFilters.size

    inner class ImageFiltersViewHolder(val binding: ItemContainerFiltersBinding) :
        RecyclerView.ViewHolder(binding.root)
}