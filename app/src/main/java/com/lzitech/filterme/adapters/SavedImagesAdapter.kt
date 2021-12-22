package com.lzitech.filterme.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzitech.filterme.databinding.ItemContainerSavedImagesBinding
import java.io.File

class SavedImagesAdapter(val savedImages: List<Pair<File, Bitmap>>) :
    RecyclerView.Adapter<SavedImagesAdapter.SavedImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedImageViewHolder {
        val binding = ItemContainerSavedImagesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedImageViewHolder, position: Int) {
        with(holder) {
            with(savedImages[position]) {
                binding.imageViewSaved.setImageBitmap(second)
            }
        }
    }

    override fun getItemCount() = savedImages.size

    inner class SavedImageViewHolder(val binding: ItemContainerSavedImagesBinding) :
        RecyclerView.ViewHolder(
            binding.root
        )
}