package org.d3if0122.assesment1mobpro.ui.screen

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.d3if0122.assesment1mobpro.R
import org.d3if0122.assesment1mobpro.ui.theme.Assesment1MobproTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier){
    val list = listOf("Matic", "Bebek", "Kopling")

    var nama by remember { mutableStateOf("") }
    var namaError by remember { mutableStateOf(false) }

    var lamaHari by remember { mutableStateOf("") }
    var lamaHariError by remember { mutableStateOf(false) }

    var hasilText by remember { mutableStateOf("") }
    val context = LocalContext.current

    fun getMotorOptionsText(context: Context, jenis: String, merk: String):String {
        val resources = context.resources
        val motorStringResId = when (jenis) {
            resources.getString(R.string.honda) -> {
                when (merk) {
                    resources.getString(R.string.matic) -> R.string.motor_honda_matic
                    resources.getString(R.string.bebek) -> R.string.motor_honda_bebek
                    resources.getString(R.string.kopling) -> R.string.motor_honda_kopling
                    else -> -1 // Handle error
                }
            }
            resources.getString(R.string.yamaha) -> {
                when (merk) {
                    resources.getString(R.string.matic) -> R.string.motor_yamaha_matic
                    resources.getString(R.string.bebek) -> R.string.motor_yamaha_bebek
                    resources.getString(R.string.kopling) -> R.string.motor_yamaha_kopling
                    else -> -1 // Handle error
                }
            }
            else -> -1 // Handle default case or error
        }
        return if (motorStringResId != -1){
            resources.getString(motorStringResId)
        } else {
            "Error: Tipe Motor tidak ada Mas Bro!"
        }
    }

    val radioOptions = listOf(
        stringResource(id = R.string.honda),
        stringResource(id = R.string.yamaha)
    )
    var merk by remember { mutableStateOf(radioOptions[0]) }

    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(list[0]) }

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = stringResource(id = R.string.sewa_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text(text = stringResource(R.string.nama_penyewa)) },
            isError = namaError,
            supportingText = { ErrorHint(namaError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lamaHari,
            onValueChange = { lamaHari = it },
            label = { Text(text = stringResource(R.string.lama_sewa)) },
            isError = lamaHariError,
            trailingIcon = { IconPicker( lamaHariError, "Hari" ) },
            supportingText = { ErrorHint( lamaHariError ) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                list.forEachIndexed{ index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            selectedText = list[index]
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

        Row (
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ){
            radioOptions.forEach { text ->
                PilihanMotor(
                    label = text,
                    isSelected = merk == text,
                    modifier = Modifier
                        .selectable(
                            selected = merk == text,
                            onClick = { merk = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }
        Button(
            onClick = {
                hasilText = getMotorOptionsText(context, merk, selectedText)
                namaError = (nama == "" || nama == "0")
                lamaHariError = (lamaHari == "" || lamaHari == "0")
                if (namaError || lamaHariError) {
                    hasilText = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.tombol_print))
        }

        Text(
            text = hasilText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
private fun PilihanMotor(label: String, isSelected: Boolean, modifier: Modifier){
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(selected = isSelected, onClick =  null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String){
    if(isError){
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean){
    if (isError){
        Text(text = stringResource(id = R.string.imput_invalid))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    Assesment1MobproTheme {
        MainScreen()
    }
}