package com.lzitech.filterme.dependencyInjection

import com.lzitech.filterme.viewModels.EditImageViewModel
import com.lzitech.filterme.viewModels.SavedImageViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        EditImageViewModel(editImageRepository = get())
    }
    viewModel {
        SavedImageViewModel(savedImageRepository = get())
    }
}