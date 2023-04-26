package com.sylvainp.ca_test.ui.BankListScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sylvainp.ca_test.R
import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import com.sylvainp.ca_test.domain.entities.BankEntity
import com.sylvainp.ca_test.ui.components.CABankHeader
import com.sylvainp.ca_test.ui.components.CABankRow
import com.sylvainp.ca_test.ui.components.CACircularProgressIndicator
import com.sylvainp.ca_test.ui.components.CAErrorText
import com.sylvainp.ca_test.ui.components.CATitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BanksScreen(navController: NavHostController, banksViewModel: BanksViewModel = viewModel()) {
    LaunchedEffect(key1 = Unit, block = {
        banksViewModel.fetchAllBanks();
    })

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { CATitle(stringResource(id = R.string.bank_screen_title)) },
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (banksViewModel.uiState.isLoading) {
                    CACircularProgressIndicator()
                }
                if (banksViewModel.uiState.error !== null) {
                    CAErrorText(text = banksViewModel.uiState.error!!);
                }
                if (banksViewModel.uiState.banksGroupedByIsCA.isNotEmpty()) {
                    BanksData(
                        groupedBanks = banksViewModel.uiState.banksGroupedByIsCA,
                        onAccountClicked = { account, bankName -> navController.navigate("banks/$bankName/${account.id}") }
                    )
                }
            }
        },
    );
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BanksData(
    groupedBanks: Map<Boolean, List<BankEntity>>,
    onAccountClicked: (account: BankAccountEntity, bankName: String) -> Unit
) {
    LazyColumn {
        groupedBanks.forEach { (isCa, banks) ->
            stickyHeader {
                CABankHeader(isCa = isCa);
            }

            items(banks) { bank ->
                CABankRow(bank, onAccountClicked)
            }
        }
    }
}

