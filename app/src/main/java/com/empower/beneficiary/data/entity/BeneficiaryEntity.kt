package com.empower.beneficiary.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Beneficiary(
    val lastName: String,
    val firstName: String,
    val designationCode: DesignationCode,
    val beneType: String,
    val socialSecurityNumber: String,
    val dateOfBirth: String,
    val phoneNumber: String,
    val beneficiaryAddress: Address
) : Parcelable

@Parcelize
data class Address(
    val firstLineMailing: String,
    val scndLineMailing: String?,
    val city: String,
    val zipCode: String,
    val stateCode: String,
    val country: String
) : Parcelable

enum class DesignationCode(val description: String) {
    P("Primary"),
    C("Contingent"),
    UNKNOWN("Unknown");

    companion object {
        fun fromCode(code: String): DesignationCode = entries.find { it.name == code } ?: UNKNOWN
    }
}