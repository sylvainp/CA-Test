package com.sylvainp.ca_test.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sylvainp.ca_test.R
import com.sylvainp.ca_test.domain.entities.BankOperationEntity
import java.text.SimpleDateFormat
import java.util.Locale

val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)

@Composable
fun CABankOperationRow(operation: BankOperationEntity) {

    Row(
        Modifier
            .background(Color.White)
            .height(IntrinsicSize.Min)
            .padding(16.dp),
    ) {
        Column() {
            Text(
                text = operation.title,
                fontSize = dimensionResource(id = R.dimen.font_size_default).value.sp,
                color = colorResource(id = R.color.default_text_color)
            )
            Text(
                modifier = Modifier.padding(16.dp, 8.dp),
                text = dateFormat.format(operation.date * 1000L),
                fontSize = dimensionResource(id = R.dimen.font_size_default).value.sp,
                color = colorResource(id = R.color.default_text_color),
            )
        }
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp, 1.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${operation.amount} €",
                fontSize = dimensionResource(id = R.dimen.font_size_default).value.sp,
                color = colorResource(id = R.color.light_grey)
            )
        }

    }
}