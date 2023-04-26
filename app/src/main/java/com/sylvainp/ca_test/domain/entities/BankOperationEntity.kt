package com.sylvainp.ca_test.domain.entities

data class BankOperationEntity(
    val id: String,
    val title: String,
    val amount: String,
    val category: String,
    val date: Long
)
