package com.example.pertemuan14.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan14.model.Mahasiswa
import com.example.pertemuan14.repository.RepositoryMhs
import kotlinx.coroutines.launch

class InsertViewModel (
    private val mhs: RepositoryMhs
): ViewModel() {

    var uiEvent: InsertUiState by mutableStateOf(InsertUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    // Memperbarui state berdasarkan input pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        uiEvent = uiEvent.copy(
            insertUiEvent = mahasiswaEvent,
        )
    }

    // validasi data input pengguna
    fun validateFields(): Boolean{
        val event = uiEvent.insertUiEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "Nim tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin =  if (event.jeniskelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong",
            judul = if (event.judul.isNotEmpty()) null else "Judul tidak boleh kosong",
            dosenPembimbing1 = if (event.dosenPembimbing1.isNotEmpty()) null else "Dosen Pembimbing 1 tidak boleh kosong",
            dosenPembimbing2 = if (event.dosenPembimbing2.isNotEmpty()) null else "Dosen Pembimbing 2 tidak boleh kosong",

        )

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertMhs() {

        if (validateFields()) {
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    mhs.insertMhs(uiEvent.insertUiEvent.toMhsModel())
                    uiState = FormState.Success("Data berhasil di simpan")
                } catch (e: Exception) {
                    uiState = FormState.Error("Data gagal disimpan")
                }
            }
        } else {
            uiState = FormState.Error("Data tidak valid")
        }
    }

    fun resetForm() {
        uiEvent = InsertUiState()
        uiState = FormState.Idle
    }

    fun resetSnackBarMessage() {
        uiState = FormState.Idle
    }
}

sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()
}

data class InsertUiState(
    val insertUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
)

data class FormErrorState(
    val nim: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null,
    val alamat: String? = null,
    val kelas: String? = null,
    val angkatan: String? = null,
    val judul: String? = null,
    val dosenPembimbing1: String? = null,
    val dosenPembimbing2: String? = null
){
    fun isValid(): Boolean{
        return nim == null && nama == null && jenisKelamin == null &&
                alamat == null && kelas == null && angkatan == null &&
                judul == null && dosenPembimbing1 == null && dosenPembimbing2 == null
    }
}

//data class variabel yang menyimpan data input form
data class  MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jeniskelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = "",
    val judul: String = "",
    val dosenPembimbing1: String = "",
    val dosenPembimbing2: String = "",
)

fun MahasiswaEvent.toMhsModel(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jenisKelamin = jeniskelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan,
    judul = judul,
    dosenPembimbing1 = dosenPembimbing1,
    dosenPembimbing2 = dosenPembimbing2
)