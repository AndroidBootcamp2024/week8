package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.ui.components.countryinfo.CountryInfoViewModel

@Composable
fun Loading(viewModel: CountryInfoViewModel?) {

    var uptime by remember { mutableStateOf(0) }
    LaunchedEffect(key1= "uptime") {
        viewModel?.counterFlow?.collect {
            uptime = it
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(text = "Loading... App uptime: ${uptime}")
        CircularProgressIndicator()
    }
}
@Preview
@Composable
fun LoadingPreview() {
    Loading(null)
}
