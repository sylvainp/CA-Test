package com.sylvainp.ca_test.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sylvainp.ca_test.R

@Composable
fun CAErrorText(text:String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = text,
        color = colorResource(id = R.color.error),
        textAlign = TextAlign.Center,
        fontSize = dimensionResource(id = R.dimen.font_size_h2).value.sp


    )
}