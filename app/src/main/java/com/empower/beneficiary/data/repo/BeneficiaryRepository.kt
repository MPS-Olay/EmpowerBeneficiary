package com.empower.beneficiary.data.repo

import com.empower.beneficiary.data.entity.Beneficiary
import com.empower.beneficiary.data.util.BeneficiaryMapper
import com.empower.beneficiary.data.util.JsonManager
import com.empower.beneficiary.util.Logger

class BeneficiaryRepository(
    private val jsonManager: JsonManager,
    private val mapper: BeneficiaryMapper,
    private val logger: Logger
) {

    suspend fun beneficiaries(): List<Beneficiary> {
        val jsonArray = jsonManager.getJsonArrayFromAsset("beneficiaries.json")
        if (jsonArray.length() == 0) logger.info("No beneficiaries found in the JSON data 🚫")
        else logger.info("Processing ${jsonArray.length()} beneficiaries 🔄")
        return mapper.toBeneficiaryList(jsonArray)
    }
}
