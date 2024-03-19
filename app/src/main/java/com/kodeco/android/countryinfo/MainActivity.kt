package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.repositories.CountryRepositoryImpl
import com.kodeco.android.countryinfo.ui.components.countryinfo.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.components.countryinfo.CountryInfoViewModel
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/v3.1/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val service: CountryService = retrofit.create(CountryService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                CountryInfoScreen(
                    viewModel = viewModel(
                        factory = CountryInfoViewModel.CountryInfoViewModelFactory(
                            repository = CountryRepositoryImpl(service),
                        ),
                    ),
                )
            }
        }
    }
}
