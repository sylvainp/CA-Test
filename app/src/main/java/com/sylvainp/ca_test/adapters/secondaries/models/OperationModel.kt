package com.sylvainp.ca_test.adapters.secondaries.models

import com.sylvainp.ca_test.domain.entities.BankOperationEntity
import kotlinx.serialization.Serializable

@Serializable
data class OperationModel(
    var id: String,
    var title: String,
    var amount: String,
    var category: String,
    var date: String
) {
    fun toDomain():BankOperationEntity {
        return BankOperationEntity(this.id, this.title, this.amount, this.category, this.date.toLong())
    }
}
