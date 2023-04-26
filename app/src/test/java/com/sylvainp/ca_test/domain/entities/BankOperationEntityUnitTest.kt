package com.sylvainp.ca_test.domain.entities

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class BankOperationEntityUnitTest {
    @Test
    fun initBankOperationEntityIsCorrect() {
        val mockDate = Date().time;
        val entity: BankOperationEntity =
            BankOperationEntity("1", "operation title", "38.5", "category", mockDate);
        assertEquals(entity.id, "1");
        assertEquals(entity.title, "operation title");
        assertEquals(entity.amount, "38.5");
        assertEquals(entity.category, "category");
        assertEquals(entity.date, mockDate);
    }
}