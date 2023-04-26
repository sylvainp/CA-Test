package com.sylvainp.ca_test.ui.BankListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sylvainp.ca_test.domain.entities.BankEntity
import com.sylvainp.ca_test.domain.usecases.GetAllBanksUsecase
import com.sylvainp.ca_test.domain.usecases.UsecaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BanksViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var getAllBanksUsecase: GetAllBanksUsecase;

    var uiState by mutableStateOf(BanksUiState())
        private set

    private var fetchAllBanksJob: Job? = null;

    fun fetchAllBanks() {
        uiState = uiState.copy(isLoading = true);
        this.fetchAllBanksJob?.cancel();
        this.fetchAllBanksJob = viewModelScope.launch(Dispatchers.IO) {
            val result: UsecaseResponse<Map<Boolean, List<BankEntity>>> =
                getAllBanksUsecase.execute();
            uiState = if (result.data !== null) {
                uiState.copy(banksGroupedByIsCA = result.data, error = null, isLoading = false)
            } else {
                uiState.copy(
                    banksGroupedByIsCA = emptyMap(),
                    error = result.error,
                    isLoading = false
                )
            }
        }
    }
}