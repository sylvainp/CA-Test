package com.sylvainp.ca_test.domain.entities

import org.junit.Assert.*
import org.junit.Test

class BankEntityUnitTest {
    @Test
    fun initBankEntityIsCorrect(){
        val bankAccountEntity: BankAccountEntity = BankAccountEntity(1, "1", "Laure Tographe",1, "123456", "Personal account", "09876", 567.4f, emptyList())
        val bankEntity : BankEntity = BankEntity("bank name", true, listOf((bankAccountEntity)))
        assertEquals(bankEntity.name, "bank name");
        assertEquals(bankEntity.isCa, true);
        assertEquals(bankEntity.accounts.size, 1);
    }
}