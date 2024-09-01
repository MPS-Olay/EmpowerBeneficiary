package com.empower.beneficiary.presentation.beneficiaryList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.empower.beneficiary.domain.GetAllBeneficiariesUseCase
import com.empower.beneficiary.util.Logger

class BeneficiaryListViewModelFactory(
    private val getAllBeneficiariesUseCase: GetAllBeneficiariesUseCase,
    private val logger: Logger
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeneficiaryListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BeneficiaryListViewModel(getAllBeneficiariesUseCase, logger) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
