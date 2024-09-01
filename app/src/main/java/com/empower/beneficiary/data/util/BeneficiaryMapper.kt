package com.empower.beneficiary.data.util

import com.empower.beneficiary.data.dto.AddressDTO
import com.empower.beneficiary.data.dto.BeneficiaryDTO
import com.empower.beneficiary.data.entity.Address
import com.empower.beneficiary.data.entity.Beneficiary
import com.empower.beneficiary.data.entity.DesignationCode
import org.json.JSONArray
import org.json.JSONObject

/**
 * Handles the transformation of JSON data into domain model objects for beneficiaries.
 * This mapper class provides functionalities to parse JSON objects into `BeneficiaryDTO` and
 * convert `BeneficiaryDTO` into `Beneficiary` domain model.
 */
class BeneficiaryMapper {

    /**
     * Converts a JSONArray into a list of Beneficiary domain models.
     *
     * @param array JSONArray containing multiple beneficiary data.
     * @return List of Beneficiary objects parsed from the JSONArray.
     */
    fun toBeneficiaryList(array: JSONArray): List<Beneficiary> = buildList {
        for (i in 0 until array.length()) {
            array.getJSONObject(i).toBeneficiaryDTO().toEntity().let { entity -> add(entity) }
        }
    }

    /**
     * Transforms a BeneficiaryDTO into a Beneficiary domain model.
     *
     * @receiver BeneficiaryDTO to be transformed.
     * @return Beneficiary object constructed from the DTO.
     */
    private fun BeneficiaryDTO.toEntity(): Beneficiary = Beneficiary(
        lastName = lastName,
        firstName = firstName,
        designationCode = DesignationCode.fromCode(designationCode),
        beneType = beneType,
        socialSecurityNumber = socialSecurityNumber,
        dateOfBirth = dateOfBirth,
        phoneNumber = phoneNumber,
        beneficiaryAddress = Address(
            firstLineMailing = beneficiaryAddress.firstLineMailing,
            scndLineMailing = beneficiaryAddress.scndLineMailing,
            city = beneficiaryAddress.city,
            zipCode = beneficiaryAddress.zipCode,
            stateCode = beneficiaryAddress.stateCode,
            country = beneficiaryAddress.country,
        )
    )

    /**
     * Parses a JSONObject into a BeneficiaryDTO.
     *
     * @receiver JSONObject containing beneficiary data.
     * @return BeneficiaryDTO parsed from the JSONObject.
     */
    private fun JSONObject.toBeneficiaryDTO(): BeneficiaryDTO {
        val addressObject = getJSONObject("beneficiaryAddress")
        val address = AddressDTO(
            firstLineMailing = addressObject.getString("firstLineMailing"),
            scndLineMailing = addressObject.optString("scndLineMailing", null),
            city = addressObject.getString("city"),
            zipCode = addressObject.getString("zipCode"),
            stateCode = addressObject.getString("stateCode"),
            country = addressObject.getString("country")
        )
        return BeneficiaryDTO(
            lastName = getString("lastName"),
            firstName = getString("firstName"),
            designationCode = getString("designationCode"),
            beneType = getString("beneType"),
            socialSecurityNumber = getString("socialSecurityNumber"),
            dateOfBirth = getString("dateOfBirth"),
            middleName = optString("middleName", null),
            phoneNumber = getString("phoneNumber"),
            beneficiaryAddress = address
        )
    }
}
