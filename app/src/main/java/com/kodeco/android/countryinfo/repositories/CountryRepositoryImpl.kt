package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.network.CountryService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CountryRepositoryImpl(val service: CountryService): CountryRepository {

    private var countries: List<Country> = listOf()
    override fun fetchCountries(): Flow<List<Country>> = flow {
        val response = service.getAllCountries()
        if (response.isSuccessful && !response.body().isNullOrEmpty()) {
            response.body()?.let {
                countries = it
            }
        } else {
            countries = listOf()
        }
        emit(countries)
    }.catch {
        emit(listOf())
    }

    override fun getCountry(name: String): Country? {
        return countries.find{ it.name.equals(name) }
    }

    public fun triggerFetchCountries() {
        fetchCountries()
    }
}