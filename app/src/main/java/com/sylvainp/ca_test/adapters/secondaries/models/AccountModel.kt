package com.sylvainp.ca_test.adapters.secondaries.models

import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import kotlinx.serialization.Serializable

@Serializable
data class AccountModel(
    var order: Int,
    var id: String,
    var holder: String,
    var role: Int,
    var contract_number: String,
    var label: String,
    var product_code: String,

    var balance: Float,
    var operations: List<OperationModel>
) {
    fun toDomain(): BankAccountEntity {
        return BankAccountEntity(
            this.order,
            this.id,
            this.holder,
            this.role,
            this.contract_number,
            this.label,
            this.product_code,
            this.balance,
            this.operations.map { it.toDomain() }
        )
    }
}