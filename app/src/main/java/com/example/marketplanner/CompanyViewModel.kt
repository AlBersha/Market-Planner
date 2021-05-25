package com.example.marketplanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplanner.api.CompanyApi
import com.example.marketplanner.models.CompanyModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    api: CompanyApi
): ViewModel() {
    private val companiesLiveData = MutableLiveData<List<CompanyModel>>()
    val companies: LiveData<List<CompanyModel>> = companiesLiveData

    init {
        viewModelScope.launch {
            val companies = api.getTickerDetails()

            companiesLiveData.value = companies
        }
    }
}