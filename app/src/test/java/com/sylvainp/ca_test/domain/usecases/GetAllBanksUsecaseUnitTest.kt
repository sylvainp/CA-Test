package com.sylvainp.ca_test.domain.usecases

import com.sylvainp.ca_test.domain.entities.BankEntity
import com.sylvainp.ca_test.domain.ports.BankPort
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllBanksUsecaseUnitTest {

    private lateinit var bankAdapterMock: BankPort
    lateinit var usecase: GetAllBanksUsecase
    private val mockBankEntity = BankEntity("bank name", true, emptyList())

    @Before
    fun initTest() {

        bankAdapterMock = mock<BankPort> {
            onBlocking { getAllBanks() } doReturn listOf(mockBankEntity)
        }
        usecase = GetAllBanksUsecase(bankAdapterMock)
    }

    @Test
    fun executeMustCallBankAdapterGetAllBanksFunction() {
        runTest {
            usecase.execute()
            verify(bankAdapterMock, times(1)).getAllBanks()
        }
    }

    @Test
    fun executeMustReturnUsecaseResponseWithAdapterResponse() {
        runTest {
            val result: UsecaseResponse<Map<Boolean, List<BankEntity>>> = usecase.execute()
            verify(bankAdapterMock, times(1)).getAllBanks()
            assertEquals(
                result.data,
                mapOf(Pair(true, listOf<BankEntity>(mockBankEntity)))
            )
            assertEquals(result.error, null)
        }
    }

    @Test
    fun executeMustReturnUsecaseResponseGroupedByIsCaProp() {
        val bank1 = BankEntity("CA Languedoc", true, emptyList());
        val bank2 = BankEntity("Green Got", false, emptyList());
        bankAdapterMock = mock<BankPort> {
            onBlocking { getAllBanks() } doReturn listOf(bank1, bank2)
        }
        usecase = GetAllBanksUsecase(bankAdapterMock);
        runTest {
            val result: UsecaseResponse<Map<Boolean, List<BankEntity>>> = usecase.execute();
            assertEquals(result.error, null);
            assertEquals(result.data?.get(true)?.size, 1);
            assertEquals(result.data?.get(true)?.get(0)?.name, "CA Languedoc");
            assertEquals(result.data?.get(false)?.size, 1);
            assertEquals(result.data?.get(false)?.get(0)?.name, "Green Got");
        }
    }

    @Test
    fun executeMustReturnUsecaseResponseGroupedByIsCaPropAndOrderByName() {
        val bank1 = BankEntity("CA Languedoc", true, emptyList());
        val bank2 = BankEntity("CA Centre-Est", true, emptyList());
        bankAdapterMock = mock<BankPort> {
            onBlocking { getAllBanks() } doReturn listOf(bank1, bank2)
        }
        usecase = GetAllBanksUsecase(bankAdapterMock);
        runTest {
            val result: UsecaseResponse<Map<Boolean, List<BankEntity>>> = usecase.execute();
            assertEquals(result.error, null);
            assertEquals(result.data?.get(true)?.size, 2);
            assertEquals(result.data?.get(true)?.get(0)?.name, "CA Centre-Est");
        }
    }

    @Test
    fun executeMustReturnUsecaseResponseWithErrorIfAdapterThrowException() {
        val failedBankAdapterMock = mock<BankPort> {
            onBlocking { getAllBanks() } doThrow Exception("An error occured")
        }
        val failedUsecase = GetAllBanksUsecase(failedBankAdapterMock)
        runTest {
            val result: UsecaseResponse<Map<Boolean, List<BankEntity>>> = failedUsecase.execute()
            assertEquals(result.data, null)
            assertEquals(result.error, "Impossible de récupérer la liste des comptes")
        }


    }
}