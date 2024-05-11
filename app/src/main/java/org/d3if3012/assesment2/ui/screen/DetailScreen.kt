package org.d3if3012.assesment2.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3012.assesment2.R
import org.d3if3012.assesment2.database.BarangDB
import org.d3if3012.assesment2.ui.theme.Assesment2Theme
import org.d3if3012.assesment2.util.ViewModelFactory

const val KEY_ID_BARANG = "idBarang"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null){
    val context = LocalContext.current
    val db = BarangDB.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var showDialog by remember { mutableStateOf(false) }

    var barang by remember {
        mutableStateOf("")
    }
    var jenis by remember {
        mutableStateOf("")
    }
    var jumlah by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true){
        if (id == null) return@LaunchedEffect
        val data = viewModel.getBarang(id) ?: return@LaunchedEffect
        barang = data.barang
        jenis = data.jenis
        jumlah = data.jumlah
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_barang))
                    else
                        Text(text = stringResource(id = R.string.edit_barang))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (barang == "" || jenis == "" || jumlah == ""){
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(barang, jenis, jumlah)
                        } else {
                            viewModel.update(id, barang, jenis, jumlah)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null){
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ){ padding ->
        FormBarang(
            goods = barang,
            onGoodsChange = {barang = it},
            type = jenis,
            onTypeChange = {jenis = it},
            modifier = Modifier.padding(padding),
            amount = jumlah,
            onAmountChange = {jumlah = it}
        )
    }
}

@Composable
fun DeleteAction(detele: () -> Unit){
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(id = R.string.opsi_lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    stringResource(R.string.hapus)
                },
                onClick = {
                    expanded = false
                    detele()
                }
            )
        }
    }
}

@Composable
fun FormBarang(
    goods: String, onGoodsChange: (String) -> Unit,
    type: String, onTypeChange: (String) -> Unit,
    amount: String, onAmountChange: (String) -> Unit,
    modifier: Modifier
) {
    val daftarJenis = listOf("Pakaian", "Makanan", "Minuman", "Komputer", "SmartPhone")
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = goods,
            onValueChange = {onGoodsChange(it) },
            label = { Text(text = stringResource(id = R.string.barang)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = type,
            onValueChange = {onTypeChange(it) },
            label = { Text(text = stringResource(id = R.string.Jumlah)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Column (
            modifier = Modifier
                .padding(vertical = 8.dp)
                .border(1.dp, Color.Gray)
                .fillMaxWidth()
        ) {
            daftarJenis.forEach { totalJumlah ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    RadioButton(
                        selected = (amount == totalJumlah),
                        onClick = { onAmountChange(totalJumlah) },
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                    )
                    Text(
                        text = totalJumlah,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Assesment2Theme {
        DetailScreen(rememberNavController())
    }
}