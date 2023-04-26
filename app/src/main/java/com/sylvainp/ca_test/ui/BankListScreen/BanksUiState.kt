package com.sylvainp.ca_test.ui.BankListScreen

import com.sylvainp.ca_test.domain.entities.BankEntity

data class BanksUiState(
    val isLoading: Boolean = false,
    val banksGroupedByIsCA: Map<Boolean,List<BankEntity>> = emptyMap<Boolean,List<BankEntity>>(),
    val error: String? = null
)
