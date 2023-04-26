package com.sylvainp.ca_test.domain.usecases

import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import com.sylvainp.ca_test.domain.entities.BankOperationEntity
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
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class GetBankAccountUsecaseUnitTest {
    private lateinit var bankAdapterMock: BankPort
    lateinit var usecase: GetBankAccountUsecase
    private val mockBankName = "Crédit Agricole"
    private val mockAccountId = "1";
    private val bankAccountMock: BankAccountEntity = BankAccountEntity(
        1,
        mockAccountId,
        "Laure Tographe",
        1,
        "123456",
        "Personal account",
        "09876",
        567.4f,
        listOf(BankOperationEntity("1", "operation title", "38.5", "category", Date().time))
    )

    @Before
    fun initTest() {
        bankAdapterMock = mock<BankPort> {
            onBlocking { getBankAccount(mockBankName, mockAccountId) } doReturn bankAccountMock
        }
        usecase = GetBankAccountUsecase(bankAdapterMock);
    }

    @Test
    fun executeMustCallBankAdapter() {
        runTest {
            usecase.execute(
                GetBankAccountUsecaseRequest(
                    bankName = mockBankName,
                    accountId = mockAccountId
                )
            );
            verify(bankAdapterMock, times(1)).getBankAccount("Crédit Agricole", "1");
        }
    }

    @Test
    fun executeMustReturnUsecaseResponseWithErrorIfNoBankNameProvided() {
        runTest {
            val response = usecase.execute(GetBankAccountUsecaseRequest(null, "2"));
            assertEquals(response.data, null);
            assertEquals(response.error, "Le nom de la banque est invalide");
        }
    }

    @Test
    fun executeMustReturnUsecaseResponseWithErrorIfNoAccountIdProvided() {
        runTest {
            val response = usecase.execute(GetBankAccountUsecaseRequest("CA", null));
            assertEquals(response.data, null);
            assertEquals(response.error, "Le numéro de compte est invalide");
        }
    }

    @Test
    fun executeMustReturnUsecaseResponseWithAccountRetrieveFromBankAdapter() {
        runTest {
            val response =
                usecase.execute(GetBankAccountUsecaseRequest(mockBankName, mockAccountId));
            assertEquals(response.data, bankAccountMock);
            assertEquals(response.error, null);
        }
    }

    @Test
    fun executeMustReturnUsecaseResponseWithErrorIfBankAdapterThrowAnException() {
        bankAdapterMock = mock<BankPort> {
            onBlocking {
                getBankAccount(
                    mockBankName,
                    mockAccountId
                )
            } doThrow Exception("An error occured")
        }
        usecase = GetBankAccountUsecase(bankAdapterMock);
        runTest {
            val result = usecase.execute(GetBankAccountUsecaseRequest(mockBankName, mockAccountId));
            assertEquals(result.error, "Impossible de récupérer le détail du compte");
            assertEquals(result.data, null);
        }
    }

    @Test
    fun executeMustReturnUsecaseResponseWithErrorIfBankAdapterReturnNull() {
        bankAdapterMock = mock<BankPort> {
            onBlocking {
                getBankAccount(
                    mockBankName,
                    mockAccountId
                )
            } doReturn null
        }
        usecase = GetBankAccountUsecase(bankAdapterMock);
        runTest {
            val result = usecase.execute(GetBankAccountUsecaseRequest(mockBankName, mockAccountId));
            assertEquals(result.error, "Impossible de récupérer le détail du compte");
            assertEquals(result.data, null);
        }
    }


}