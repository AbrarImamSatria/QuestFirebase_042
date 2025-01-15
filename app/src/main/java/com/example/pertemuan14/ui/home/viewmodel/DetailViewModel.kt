package com.example.pertemuan14.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14.model.Mahasiswa
import com.example.pertemuan14.repository.RepositoryMhs
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {
    var detailUIState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    fun getMhsByNim(nim: String) {
        viewModelScope.launch {
            repositoryMhs.getMhs(nim)
                .onStart {
                    detailUIState = DetailUiState.Loading
                }
                .catch { error ->
                    detailUIState = DetailUiState.Error(error)
                }
                .collect { mahasiswa ->
                    detailUIState = DetailUiState.Success(mahasiswa)
                }
        }
    }
}

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val data: Mahasiswa) : DetailUiState()
    data class Error(val error: Throwable) : DetailUiState()
}