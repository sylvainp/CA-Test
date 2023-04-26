package com.sylvainp.ca_test.domain.entities

data class BankEntity(val name:String, val isCa: Boolean, val accounts: List<BankAccountEntity>)
