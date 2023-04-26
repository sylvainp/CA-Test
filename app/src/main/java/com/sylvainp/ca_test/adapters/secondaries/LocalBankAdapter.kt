package com.sylvainp.ca_test.adapters.secondaries

import android.content.Context
import com.sylvainp.ca_test.adapters.secondaries.models.BankModel
import com.sylvainp.ca_test.adapters.secondaries.models.JSONBankModel
import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import com.sylvainp.ca_test.domain.entities.BankEntity
import com.sylvainp.ca_test.domain.ports.BankPort
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LocalBankAdapter @Inject constructor(@ApplicationContext context: Context) : BankPort {
    private var banks: List<BankModel>;

    init {
        val fileInString: String =
            context.assets.open("banks.json").bufferedReader().use { it.readText() }
        val bankModelList: JSONBankModel = Json.decodeFromString<JSONBankModel>(fileInString)
        banks = bankModelList.banks
    }

    override suspend fun getAllBanks(): List<BankEntity> {
        return this.banks.map { it.toDomain() }
    }

    override suspend fun getBankAccount(bankName: String, accountId: String): BankAccountEntity? {
        val targetBank: BankModel? = this.banks.firstOrNull { it.name == bankName };
        return targetBank?.accounts?.firstOrNull { it.id == accountId }
            ?.toDomain()
    }
}