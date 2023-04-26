package com.sylvainp.ca_test.domain.usecases

import com.sylvainp.ca_test.domain.entities.BankAccountEntity
import com.sylvainp.ca_test.domain.ports.BankPort
import javax.inject.Inject

class GetBankAccountUsecase @Inject constructor(private val bankPort: BankPort) :
    Usecase<GetBankAccountUsecaseRequest, BankAccountEntity>() {
    override suspend fun execute(request: GetBankAccountUsecaseRequest?): UsecaseResponse<BankAccountEntity> {
        if (request?.accountId == null) {
            return UsecaseResponse(error = "Le numéro de compte est invalide", data = null)
        }
        if (request.bankName == null) {
            return UsecaseResponse(error = "Le nom de la banque est invalide", data = null)
        }
        return try {
            val response = this.bankPort.getBankAccount(request.bankName!!, request.accountId!!)
            return if (response == null) {
                UsecaseResponse(error = "Impossible de récupérer le détail du compte", data = null);
            } else {
                UsecaseResponse(data = response, error = null);

            }
        } catch (error: Exception) {
            error.printStackTrace()
            UsecaseResponse(error = "Impossible de récupérer le détail du compte", data = null)
        }
    }
}