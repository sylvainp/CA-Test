package com.sylvainp.ca_test.adapters.secondaries.models

import kotlinx.serialization.Serializable

@Serializable
data class JSONBankModel(
    var banks: List<BankModel>
);