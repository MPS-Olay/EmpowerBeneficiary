package com.empower.beneficiary.presentation.beneficiaryList

import com.empower.beneficiary.data.entity.Address
import com.empower.beneficiary.data.entity.Beneficiary
import com.empower.beneficiary.data.entity.DesignationCode
import com.empower.beneficiary.domain.GetAllBeneficiariesUseCase
import com.empower.beneficiary.presentation.beneficiaryList.viewmodel.BeneficiaryListState
import com.empower.beneficiary.presentation.beneficiaryList.viewmodel.BeneficiaryListViewModel
import com.empower.beneficiary.presentation.beneficiaryList.viewmodel.BeneficiaryListViewModelFactory
import com.empower.beneficiary.testUtil.MainDispatcherExtension
import com.empower.beneficiary.util.Logger
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExtendWith(MockKExtension::class)
class BeneficiaryListViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()
    private lateinit var getAllBeneficiariesUseCase: GetAllBeneficiariesUseCase
    private lateinit var logger: Logger
    private lateinit var viewModel: BeneficiaryListViewModel

    @BeforeEach
    fun setUp() {
        getAllBeneficiariesUseCase = mockk()
        logger = mockk(relaxed = true)
        coEvery { getAllBeneficiariesUseCase() } returns emptyList()
        viewModel = BeneficiaryListViewModelFactory(getAllBeneficiariesUseCase, logger).create(BeneficiaryListViewModel::class.java)
    }

    @Test
    @DisplayName("🚀 ViewModel initializes and loads beneficiaries successfully")
    fun `ViewModel initializes and loads beneficiaries successfully`() =
        runTest(mainDispatcherExtension.testDispatcher) {
            // Given
            val expectedBeneficiaries = listOf(
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
            coEvery { getAllBeneficiariesUseCase() } returns expectedBeneficiaries

            // When
            viewModel = BeneficiaryListViewModel(getAllBeneficiariesUseCase, logger)
            val stateUpdates = mutableListOf<BeneficiaryListState>()
            val job = launch { viewModel.beneficiaries.toList(stateUpdates) }

            // Then
            val lastState = stateUpdates.last()
            assertAll(
                {
                    assertEquals(
                        expectedBeneficiaries,
                        lastState.beneficiaries,
                        "Beneficiaries should match expected list"
                    )
                },
                { assertFalse(lastState.isLoading, "isLoading should be false") },
                { assertNull(lastState.error, "error should be null") }
            )
            job.cancel()
        }

    @Test
    @DisplayName("🛑 ViewModel handles errors during beneficiary loading")
    fun `ViewModel handles errors on initialization`() =
        runTest(mainDispatcherExtension.testDispatcher) {
            // Given
            val exception = RuntimeException("Failed to load")
            coEvery { getAllBeneficiariesUseCase() } throws exception

            // When
            viewModel = BeneficiaryListViewModel(getAllBeneficiariesUseCase, logger)
            val stateUpdates = mutableListOf<BeneficiaryListState>()
            val job = launch { viewModel.beneficiaries.toList(stateUpdates) }

            // Then
            val lastState = stateUpdates.last()
            assertAll(
                { assertTrue(lastState.error?.isNotBlank() == true, "error should not be blank") },
                { assertFalse(lastState.isLoading, "isLoading should be false") },
                {
                    assertEquals(
                        "Failed to load data ❌",
                        lastState.error,
                        "Error message should match"
                    )
                }
            )
            job.cancel()
        }
}
