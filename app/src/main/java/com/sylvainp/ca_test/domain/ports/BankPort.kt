package com.sylvainp.ca_test.domain.ports

import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import com.sylvainp.ca_test.domain.entities.BankEntity
import kotlin.jvm.Throws

interface BankPort {
    @Throws(Exception::class)
    suspend fun getAllBanks(): List<BankEntity>
    @Throws(Exception::class)
    suspend fun getBankAccount(bankName:String, accountId: String): BankAccountEntity?;
}