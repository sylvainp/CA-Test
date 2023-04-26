package com.sylvainp.ca_test.adapters.secondaries.models

import com.sylvainp.ca_test.domain.entities.BankEntity
import kotlinx.serialization.Serializable

@Serializable
data class BankModel (
    var name: String,
    var isCA: Int,
    var accounts: List<AccountModel>
) {
    fun toDomain():BankEntity {
        return BankEntity(this.name, this.isCA == 1, this.accounts.map { it.toDomain() })
    }
}