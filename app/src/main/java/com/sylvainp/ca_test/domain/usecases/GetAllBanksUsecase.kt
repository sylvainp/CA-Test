package com.sylvainp.ca_test.domain.usecases

import com.sylvainp.ca_test.domain.entities.BankEntity
import com.sylvainp.ca_test.domain.ports.BankPort
import javax.inject.Inject

class GetAllBanksUsecase @Inject constructor(private val bankPort: BankPort) :
    Usecase<Unit, Map<Boolean, List<BankEntity>>>() {

    override suspend fun execute(params: Unit?): UsecaseResponse<Map<Boolean, List<BankEntity>>> {
        return try {
            val response: List<BankEntity> = this.bankPort.getAllBanks();
            UsecaseResponse(
                data = response.sortedWith(compareByDescending<BankEntity> { it.isCa }.thenBy { it.name })
                    .groupBy { it.isCa },
                error = null
            );
        } catch (error: Exception) {
            error.printStackTrace();
            UsecaseResponse(error = "Impossible de récupérer la liste des comptes", data = null);
        }
    }
}