package com.example.pertemuan14.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan14.MahasiswaApplications
import com.example.pertemuan14.model.Mahasiswa
import com.example.pertemuan14.ui.home.viewmodel.DetailViewModel
import com.example.pertemuan14.ui.home.viewmodel.HomeViewModel
import com.example.pertemuan14.ui.home.viewmodel.InsertViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                mahasiswaApp().container.repositoryMhs
            )
        }

        initializer {
            InsertViewModel(
                mahasiswaApp().container.repositoryMhs
            )
        }

        initializer {
            DetailViewModel(
                mahasiswaApp().container.repositoryMhs
            )
        }

    }
}
fun CreationExtras.mahasiswaApp(): MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)