package com.kodeco.android.countryinfo.ui.components.countryinfo

import android.os.Parcelable
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Error
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class CountryInfoState : Parcelable {
    data object Loading : CountryInfoState()
    data class Success(val countries: List<Country>) : CountryInfoState()
    data class Error(val error: Throwable) : CountryInfoState()
}

@Composable
fun CountryInfoScreen(
    viewModel: CountryInfoViewModel?,
) {
    val state = viewModel?.uiState?.collectAsState()
    Surface {
        when(val curState = state!!.value) {
            is CountryInfoState.Loading -> Loading(viewModel)
            is CountryInfoState.Success -> CountryInfoList(curState.countries, viewModel) {
                viewModel.setLoadingState()
            }
            is CountryInfoState.Error -> Error(curState.error) {
                viewModel.setLoadingState()
            }
        }
    }
}

@Preview
@Composable
fun CountryInfoScreenPreview() {
    CountryInfoScreen(null)
}
