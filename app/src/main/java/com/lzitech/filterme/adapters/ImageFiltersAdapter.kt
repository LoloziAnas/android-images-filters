package com.lzitech.filterme.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzitech.filterme.data.ImageFilter
import com.lzitech.filterme.databinding.ItemContainerFiltersBinding
import com.lzitech.filterme.listeners.ImageFiltersListener

class ImageFiltersAdapter(
    private val imageFilters: List<ImageFilter>,
    private val imageFiltersListener: ImageFiltersListener
) :
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
                binding.root.setOnClickListener{
                    imageFiltersListener.onFilterSelected(this)
                }
            }
        }
    }

    override fun getItemCount() = imageFilters.size

    inner class ImageFiltersViewHolder(val binding: ItemContainerFiltersBinding) :
        RecyclerView.ViewHolder(binding.root)
}