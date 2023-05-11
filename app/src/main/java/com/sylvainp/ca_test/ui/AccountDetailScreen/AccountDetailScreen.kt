package com.sylvainp.ca_test.ui.AccountDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sylvainp.ca_test.R
import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import com.sylvainp.ca_test.domain.entities.BankOperationEntity
import com.sylvainp.ca_test.ui.components.CABankOperationRow
import com.sylvainp.ca_test.ui.components.CACircularProgressIndicator
import com.sylvainp.ca_test.ui.components.CAErrorText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailScreen(
    navController: NavHostController,
    accountDetailViewModel: AccountDetailViewModel = viewModel(),
    bankName: String?,
    accountId: String?
) {

    LaunchedEffect(key1 = Unit, block = {
        accountDetailViewModel.fetchAccountDetail(bankName, accountId)
    });

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController = navController)
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (accountDetailViewModel.uiState.isLoading) {
                    CACircularProgressIndicator();
                }
                if (accountDetailViewModel.uiState.error !== null) {
                    CAErrorText(accountDetailViewModel.uiState.error!!)
                }
                if (accountDetailViewModel.uiState.account !== null) {
                    AccountData(accountDetailViewModel.uiState.account!!)
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.bank_screen_title),
                color = colorResource(id = R.color.topbar_back_color)
            )
        },
        navigationIcon = {
            if (navController.previousBackStackEntry !== null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.topbar_back_color)
                    )
                }
            }
        }

    )
}

@Composable
fun AccountData(accountInfo: BankAccountEntity) {
    Column() {
        val balanceColor =
            if (accountInfo.balance > 0) {
                colorResource(id = R.color.bank_balance_positive_color)
            } else {
                colorResource(
                    id = R.color.bank_balance_negative_color
                )
            }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp, 20.dp, 10.dp),
            textAlign = TextAlign.Center,
            text = "${accountInfo.balance} €",
            fontSize = dimensionResource(id = R.dimen.font_size_h1).value.sp,
            fontWeight = FontWeight.Bold,
            color = balanceColor
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 20.dp),
            text = accountInfo.label,
            textAlign = TextAlign.Center,
            fontSize = dimensionResource(id = R.dimen.font_size_h2).value.sp,
            color = colorResource(id = R.color.bank_name_color)
        )
        val sortedOperation: List<BankOperationEntity> = accountInfo.sortedOperation();
        LazyColumn(verticalArrangement = Arrangement.spacedBy(1.dp),
            content = {
                items(sortedOperation) { operation ->
                    CABankOperationRow(operation)
                }
            })
    }

}