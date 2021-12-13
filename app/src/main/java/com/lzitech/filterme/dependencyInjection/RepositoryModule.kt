package com.lzitech.filterme.dependencyInjection

import com.lzitech.filterme.repositories.EditImageRepository
import com.lzitech.filterme.repositories.EditImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule  = module{
    factory<EditImageRepository> {EditImageRepositoryImpl(androidContext())}
}