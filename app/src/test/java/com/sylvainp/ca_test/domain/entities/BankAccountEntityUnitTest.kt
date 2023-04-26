package com.sylvainp.ca_test.domain.entities

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class BankAccountEntityUnitTest {

    @Test
    fun initBankAccountEntityIsCorrect() {
        val mockDate = Date().time;
        val bankOperationEntity =
            BankOperationEntity("1", "operation title", "38.5", "category", mockDate);
        val bankAccountEntity: BankAccountEntity = BankAccountEntity(
            1,
            "1",
            "Laure Tographe",
            1,
            "123456",
            "Personal account",
            "09876",
            567.4f,
            listOf(bankOperationEntity)
        )
        assertEquals(bankAccountEntity.order, 1);
        assertEquals(bankAccountEntity.id, "1");
        assertEquals(bankAccountEntity.holder, "Laure Tographe");
        assertEquals(bankAccountEntity.contract_number, "123456");
        assertEquals(bankAccountEntity.label, "Personal account");
        assertEquals(bankAccountEntity.product_code, "09876");
        assertEquals(bankAccountEntity.balance, 567.4f);
        assertEquals(bankAccountEntity.operations.size, 1)
    }

    @Test
    fun sortedOperationReturnOperationSortedByDateDesc(){
        val operation1 =
            BankOperationEntity("1", "operation title 1", "38.5", "category", 1682401414L);
        val operation2 =
            BankOperationEntity("2", "operation title 2", "38.5", "category", 1682487814L);
        val bankAccountEntity: BankAccountEntity = BankAccountEntity(
            1,
            "1",
            "Laure Tographe",
            1,
            "123456",
            "Personal account",
            "09876",
            567.4f,
            listOf(operation1, operation2)
        )
        val sortedOperation: List<BankOperationEntity> = bankAccountEntity.sortedOperation();
        assertEquals(sortedOperation[0].id, "2")
        assertEquals(sortedOperation[1].id, "1")
    }

    @Test
    fun sortedOperationReturnOperationSortedByNameIfSameDateDesc(){
        val operation1 =
            BankOperationEntity("1", "operation B", "38.5", "category", 1682401414L);
        val operation2 =
            BankOperationEntity("2", "operation A", "38.5", "category", 1682401414L);
        val bankAccountEntity: BankAccountEntity = BankAccountEntity(
            1,
            "1",
            "Laure Tographe",
            1,
            "123456",
            "Personal account",
            "09876",
            567.4f,
            listOf(operation1, operation2)
        )
        val sortedOperation: List<BankOperationEntity> = bankAccountEntity.sortedOperation();
        assertEquals(sortedOperation[0].id, "2")
        assertEquals(sortedOperation[1].id, "1")
    }
}