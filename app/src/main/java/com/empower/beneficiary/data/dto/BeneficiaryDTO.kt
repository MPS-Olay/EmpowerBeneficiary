package com.empower.beneficiary.data.dto

data class BeneficiaryDTO(
    val lastName: String,
    val firstName: String,
    val designationCode: String,
    val beneType: String,
    val socialSecurityNumber: String,
    val dateOfBirth: String,
    val middleName: String?,
    val phoneNumber: String,
    val beneficiaryAddress: AddressDTO
)

data class AddressDTO(
    val firstLineMailing: String,
    val scndLineMailing: String?,
    val city: String,
    val zipCode: String,
    val stateCode: String,
    val country: String
)