package com.lzitech.filterme.dependencyInjection

import com.lzitech.filterme.repositories.EditImageRepository
import com.lzitech.filterme.repositories.EditImageRepositoryImpl
import com.lzitech.filterme.repositories.SavedImageRepository
import com.lzitech.filterme.repositories.SavedImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule  = module {
    factory<EditImageRepository> { EditImageRepositoryImpl(androidContext()) }
    factory<SavedImageRepository> { SavedImageRepositoryImpl(androidContext()) }

}