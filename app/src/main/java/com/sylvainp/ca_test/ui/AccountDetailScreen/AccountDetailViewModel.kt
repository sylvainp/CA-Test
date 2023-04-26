package com.sylvainp.ca_test.ui.AccountDetailScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import com.sylvainp.ca_test.domain.usecases.GetAllBanksUsecase
import com.sylvainp.ca_test.domain.usecases.GetBankAccountUsecase
import com.sylvainp.ca_test.domain.usecases.GetBankAccountUsecaseRequest
import com.sylvainp.ca_test.domain.usecases.UsecaseResponse
import com.sylvainp.ca_test.ui.BankListScreen.BanksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDetailViewModel @Inject constructor(): ViewModel() {
    @Inject lateinit var getBankAccountUsecase: GetBankAccountUsecase;

    var uiState by mutableStateOf(AccountDetailUiState())
        private set

    private var fetchBankAccountJob: Job? = null;

    fun fetchAccountDetail(bankName:String?, accountId:String?) {
        uiState = uiState.copy(isLoading = true);
        fetchBankAccountJob?.cancel();
        this.fetchBankAccountJob = viewModelScope.launch {
            val result: UsecaseResponse<BankAccountEntity> = getBankAccountUsecase.execute(
                GetBankAccountUsecaseRequest(bankName, accountId)
            );
            uiState = if (result.data !== null) {
                uiState.copy(account = result.data, error=null, isLoading = false)
            } else {
                uiState.copy(account = null, error = result.error, isLoading = false)
            }
        }

    }
}