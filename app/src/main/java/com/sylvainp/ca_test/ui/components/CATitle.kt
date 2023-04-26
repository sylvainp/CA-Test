package com.sylvainp.ca_test.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sylvainp.ca_test.R

@Composable
fun CATitle(title: String) {
    Text(
        text = title,
        fontSize = dimensionResource(id = R.dimen.font_size_h1).value.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
}