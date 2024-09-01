package com.empower.beneficiary.domain

import com.empower.beneficiary.data.repo.BeneficiaryRepository
import com.empower.beneficiary.util.Logger

class GetAllBeneficiariesUseCase(
    private val repo: BeneficiaryRepository,
    private val logger: Logger
) {

    suspend operator fun invoke() = repo.beneficiaries()
        .also { beneficiaries ->
            if (beneficiaries.isEmpty()) logger.debug("No beneficiaries found 🚫")
            else logger.debug("Retrieved beneficiaries: ${beneficiaries.size} found ✅")
        }
}
