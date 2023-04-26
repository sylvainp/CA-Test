package com.sylvainp.ca_test.ui.AccountDetailScreen

import com.sylvainp.ca_test.domain.entities.BankAccountEntity

data class AccountDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val account: BankAccountEntity? = null
)
