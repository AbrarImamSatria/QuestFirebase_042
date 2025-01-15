package com.example.pertemuan14.ui.home.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan14.R
import com.example.pertemuan14.model.Mahasiswa
import com.example.pertemuan14.ui.PenyediaViewModel
import com.example.pertemuan14.ui.home.viewmodel.DetailUiState
import com.example.pertemuan14.ui.home.viewmodel.DetailViewModel
import com.example.pertemuan14.ui.home.viewmodel.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    nim: String,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    LaunchedEffect(nim) {
        viewModel.getMhsByNim(nim)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Tambah Mahasiswa") },
                navigationIcon = {
                    Button(onClick = navigateBack) {
                        Text("Back")
                    }
                }
            )
        },
    ) { innerPadding ->
        DetailStatus(
            detailUiState = viewModel.detailUIState,
            retryAction = { viewModel.getMhsByNim(nim) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun DetailStatus(
    detailUiState: DetailUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoadingDetail(modifier = modifier.fillMaxSize())
        is DetailUiState.Success -> DetailLayout(
            mahasiswa = detailUiState.data,
            modifier = modifier.fillMaxWidth()
        )
        is DetailUiState.Error -> OnErrorDetail(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
private fun OnErrorDetail(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Coba lagi",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun OnLoadingDetail(modifier: Modifier = Modifier) {
    Text("Loading .......")
}

@Composable
fun DetailLayout(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "NIM: ${mahasiswa.nim}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Nama: ${mahasiswa.nama}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Alamat: ${mahasiswa.alamat}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Jenis Kelamin: ${mahasiswa.jenisKelamin}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Kelas: ${mahasiswa.kelas}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Angkatan: ${mahasiswa.angkatan}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Judul: ${mahasiswa.judul}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Dosen Pembimbing 1: ${mahasiswa.dosenPembimbing1}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Dosen Pembimbing 2: ${mahasiswa.dosenPembimbing2}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}