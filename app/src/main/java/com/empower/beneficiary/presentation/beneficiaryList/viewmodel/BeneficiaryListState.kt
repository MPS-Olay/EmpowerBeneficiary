package com.empower.beneficiary.presentation.beneficiaryList.viewmodel

import com.empower.beneficiary.data.entity.Beneficiary

data class BeneficiaryListState(
    val beneficiaries: List<Beneficiary> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
