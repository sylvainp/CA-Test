package com.sylvainp.ca_test.domain.entities

data class BankAccountEntity(
    val order: Int,
    val id: String,
    val holder: String,
    val role: Int,
    val contract_number: String,
    val label: String,
    val product_code: String,
    val balance: Float,
    val operations: List<BankOperationEntity>
) {
    fun sortedOperation(): List<BankOperationEntity> {
        return this.operations.sortedWith(compareByDescending<BankOperationEntity> { it.date }.thenBy { it.title })
    }
}
