package com.breno.evernotekt.di

import com.breno.evernotekt.data.NoteRepository
import com.breno.evernotekt.data.NoteRepositoryImpl
import com.breno.evernotekt.data.model.RemoteDataSource
import com.breno.evernotekt.viewmodel.AddViewModel
import com.breno.evernotekt.viewmodel.HomeViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val repositoryModule = module {
    factory {
        RemoteDataSource()
    }
    single {
        CompositeDisposable()
    }
    factory<NoteRepository> {
        NoteRepositoryImpl(remoteDataSource = get(), compositeDisposable = get())
    }
}

private val viewModelModule = module {
    viewModel {
        HomeViewModel(repository = get())
    }
    viewModel {
        AddViewModel(repository = get())
    }
}

val modules = listOf(repositoryModule, viewModelModule)

