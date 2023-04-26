package com.sylvainp.ca_test.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sylvainp.ca_test.R
import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import com.sylvainp.ca_test.domain.entities.BankEntity

@Composable
fun CABankRow(
    bank: BankEntity,
    onAccountClick: (account: BankAccountEntity, bankName: String) -> Unit
) {
    var isDeployed by rememberSaveable { mutableStateOf(false) }
    Column() {
        BankSumInfo(bank = bank, isDeployed) { isDeployed = !isDeployed };
        if (isDeployed) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp, 8.dp, 16.dp, 8.dp)
            ) {
                bank.accounts.forEach {
                    AccountRow(account = it, bank.name, onAccountClick);
                }
            }
        }
    }
}

@Composable
fun BankSumInfo(bank: BankEntity, isDeployed: Boolean, onRowPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp, 8.dp, 16.dp, 8.dp)
            .clickable { onRowPressed() }
    ) {
        Text(
            text = bank.name,
            fontSize = dimensionResource(id = R.dimen.font_size_h2).value.sp,
            color = colorResource(id = R.color.default_text_color)
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = "${bank.accounts.map { it.balance }.sum()} €",
            fontSize = dimensionResource(id = R.dimen.font_size_h2).value.sp,
            color = colorResource(id = R.color.light_grey)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            if (isDeployed) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "collapse",
                    tint = colorResource(id = R.color.light_grey),
                    modifier = Modifier.padding(16.dp, 0.dp)

                )
            } else {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "expand",
                    tint = colorResource(id = R.color.light_grey),
                    modifier = Modifier.padding(16.dp, 0.dp)
                )
            }
        }


    }
}

@Composable
fun AccountRow(
    account: BankAccountEntity,
    bankName: String,
    onAccountClick: (account: BankAccountEntity, bankName: String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(0.dp, 16.dp)
            .clickable { onAccountClick(account, bankName) }
    ) {
        Text(
            text = account.label,
            fontSize = dimensionResource(id = R.dimen.font_size_default).value.sp,
            color = colorResource(id = R.color.default_text_color)
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = "${account.balance} €",
            fontSize = dimensionResource(id = R.dimen.font_size_default).value.sp,
            color = colorResource(id = R.color.light_grey)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(0.dp, 1.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "expand",
                tint = colorResource(id = R.color.light_grey),
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun CABankRowPreview() {
    val accounts = listOf<BankAccountEntity>(
        BankAccountEntity(
            1,
            "1",
            "Sarah Croche",
            1,
            "1A",
            "Compte de dépôt",
            "1A",
            500f,
            emptyList()
        ), BankAccountEntity(
            2,
            "2",
            "Laure Tographe",
            1,
            "2B",
            "Compte Chèque",
            "2B",
            300f,
            emptyList()
        )
    )
    val bankEntity = BankEntity("CA Centre-Est", true, accounts);
    CABankRow(bank = bankEntity, onAccountClick = { account, bankName -> print("clicked") });
}