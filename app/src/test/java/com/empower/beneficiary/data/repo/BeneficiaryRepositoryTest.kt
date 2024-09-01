package com.empower.beneficiary.data.repo

import com.empower.beneficiary.data.entity.Address
import com.empower.beneficiary.data.entity.Beneficiary
import com.empower.beneficiary.data.entity.DesignationCode
import com.empower.beneficiary.data.util.BeneficiaryMapper
import com.empower.beneficiary.data.util.JsonManager
import com.empower.beneficiary.util.Logger
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class BeneficiaryRepositoryTest {

    private lateinit var jsonManager: JsonManager
    private lateinit var mapper: BeneficiaryMapper
    private lateinit var logger: Logger
    private lateinit var repository: BeneficiaryRepository

    @BeforeEach
    fun setUp() {
        jsonManager = mockk()
        mapper = mockk()
        logger = mockk(relaxed = true)
        repository = BeneficiaryRepository(jsonManager, mapper, logger)
    }

    @Test
    @DisplayName("✅ Validates that beneficiaries are returned when JSON is valid")
    fun `beneficiaries returns list of beneficiaries when JSON is valid`() = runTest {
        // Given
        val jsonArray = mockk<JSONArray>()
        val jsonObject = mockk<JSONObject>()

        every { jsonObject.getString("firstName") } returns "John"
        every { jsonObject.getString("lastName") } returns "Doe"
        every { jsonArray.length() } returns 1
        every { jsonArray.getJSONObject(0) } returns jsonObject

        coEvery { jsonManager.getJsonArrayFromAsset("beneficiaries.json") } returns jsonArray
        every { mapper.toBeneficiaryList(jsonArray) } returns listOf(
            Beneficiary(
                "Doe",
                "John",
                DesignationCode.P,
                "Spouse",
                "XXXXX3333",
                "01011980",
                "3035551234",
                Address("123 Main St", null, "Denver", "80202", "CO", "US")
            )
        )

        // When
        val beneficiaries = repository.beneficiaries()

        // Then
        Assertions.assertEquals(1, beneficiaries.size)
        Assertions.assertEquals("John", beneficiaries[0].firstName)
    }

    @Test
    @DisplayName("🚫 Verifies info log when JSON array is empty")
    fun `beneficiaries logs info when JSON array is empty`() = runTest {
        // Given
        val emptyJsonArray = mockk<JSONArray>()
        every { emptyJsonArray.length() } returns 0

        coEvery { jsonManager.getJsonArrayFromAsset("beneficiaries.json") } returns emptyJsonArray
        every { mapper.toBeneficiaryList(emptyJsonArray) } returns emptyList()

        // When
        val beneficiaries = repository.beneficiaries()

        // Then
        Assertions.assertTrue(beneficiaries.isEmpty())
    }
}