package com.sylvainp.ca_test.adapters.secondaries

import com.sylvainp.ca_test.adapters.secondaries.models.BankModel
import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import com.sylvainp.ca_test.domain.entities.BankEntity
import com.sylvainp.ca_test.domain.ports.BankPort
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class RemoteBankAdapter @Inject constructor() : BankPort {
    private val clientHttp: OkHttpClient = OkHttpClient();
    private val url: String =
        "https://cdf-test-mobile-default-rtdb.europe-west1.firebasedatabase.app/banks.json"

    private var banks: List<BankModel> = emptyList();

    init {
        println("RemoteBankAdapter init !")
    }

    override suspend fun getAllBanks(): List<BankEntity> {
        val request = Request.Builder().url(url).build()
        val response = clientHttp.newCall(request).execute()
        if (response.code != 200) {
            throw Exception("Une erreur est survenue lors de la récupération des données")
        }
        val responseString = response.body?.string()
        if (responseString !== null) {
            banks = Json.decodeFromString<List<BankModel>>(responseString)
        }
        return banks.map { it.toDomain() };
    }

    override suspend fun getBankAccount(bankName: String, accountId: String): BankAccountEntity? {
        return banks.firstOrNull { it.name == bankName }?.accounts?.firstOrNull { it.id == accountId }
            ?.toDomain()
    }
}