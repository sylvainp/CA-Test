package com.sylvainp.ca_test.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sylvainp.ca_test.R

@Composable
fun CABankHeader(isCa: Boolean) {
    var text =
        if (isCa) {
            stringResource(id = R.string.credit_agricole)
        } else {
            stringResource(id = R.string.other_bank)
        };
    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(0.dp, 16.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(id = R.color.bank_header_start_color),
                        colorResource(id = R.color.bank_header_end_color)
                    )
                )
            )
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp, 0.dp)
                .align(Alignment.CenterStart),
            text = text,
            color = colorResource(id = R.color.white),
            fontSize = dimensionResource(id = R.dimen.font_size_h2).value.sp
        );
    }
}