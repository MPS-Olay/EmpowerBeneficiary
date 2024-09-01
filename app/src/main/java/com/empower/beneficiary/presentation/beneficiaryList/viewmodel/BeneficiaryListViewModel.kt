package com.empower.beneficiary.presentation.beneficiaryList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.empower.beneficiary.domain.GetAllBeneficiariesUseCase
import com.empower.beneficiary.util.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BeneficiaryListViewModel(
    private val getAllBeneficiariesUseCase: GetAllBeneficiariesUseCase,
    private val logger: Logger
) : ViewModel() {

    private val _beneficiaries = MutableStateFlow(BeneficiaryListState())
    val beneficiaries: StateFlow<BeneficiaryListState> get() = _beneficiaries

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logger.error("Something went wrong!", throwable)
        _beneficiaries.update { it.copy(error = "Failed to load data ❌", isLoading = false) }
    }

    init {
        loadBeneficiaries()
    }

    /**
     * Loads beneficiaries from the use case and updates the UI state accordingly.
     */
    private fun loadBeneficiaries() {
        logger.debug("Attempting to load beneficiaries 🔄")
        viewModelScope.launch(exceptionHandler) {
            val beneficiaries = getAllBeneficiariesUseCase()
            logger.info("Beneficiaries loaded successfully ✅")
            _beneficiaries.update { it.copy(beneficiaries = beneficiaries, isLoading = false) }
        }
    }
}
