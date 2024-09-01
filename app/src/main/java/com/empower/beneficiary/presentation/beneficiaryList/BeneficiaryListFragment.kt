package com.empower.beneficiary.presentation.beneficiaryList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.empower.beneficiary.data.entity.Beneficiary
import com.empower.beneficiary.data.repo.BeneficiaryRepository
import com.empower.beneficiary.data.util.AssetJsonManager
import com.empower.beneficiary.data.util.BeneficiaryMapper
import com.empower.beneficiary.domain.GetAllBeneficiariesUseCase
import com.empower.beneficiary.presentation.MainActivity
import com.empower.beneficiary.presentation.beneficiaryDetail.BeneficiaryDetailFragment
import com.empower.beneficiary.presentation.beneficiaryList.viewmodel.BeneficiaryListViewModel
import com.empower.beneficiary.presentation.beneficiaryList.viewmodel.BeneficiaryListViewModelFactory
import com.empower.beneficiary.util.SimpleLogger
import com.empower.beneficiary.util.fullName
import com.empower.beneficiary.util.getThemePrimaryColor
import kotlinx.coroutines.launch

class BeneficiaryListFragment : Fragment() {
    private val logger = SimpleLogger("BeneficiaryListFragment")

    private val viewModel: BeneficiaryListViewModel by viewModels {
        val beneficiaryRepository = BeneficiaryRepository(
            jsonManager = AssetJsonManager(requireContext()),
            mapper = BeneficiaryMapper(),
            logger = logger.withTag("BeneficiaryRepository")
        )
        val getAllBeneficiariesUseCase = GetAllBeneficiariesUseCase(
            repo = beneficiaryRepository,
            logger = logger.withTag("GetAllBeneficiariesUseCase")
        )
        BeneficiaryListViewModelFactory(
            getAllBeneficiariesUseCase = getAllBeneficiariesUseCase,
            logger = logger.withTag("BeneficiaryListViewModel")
        )
    }


    private val beneficiaryAdapter by lazy {
        BeneficiaryAdapter(
            logger = logger.withTag("BeneficiaryAdapter"),
            beneficiaryClicked = ::showBeneficiaryDetail
        )
    }
    private val recyclerView: RecyclerView by lazy { initRecyclerView() }
    private val progressBar: ProgressBar by lazy { initProgressBar() }
    private val errorTextView: TextView by lazy { initErrorTextView() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initMainLayout()
        .apply {
            logger.debug("Creating views for BeneficiaryListFragment 🛠️")
            addView(initToolbar())
            addView(initContentLayout())
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        logger.info("BeneficiaryListFragment views created and observers setup 🎬")
    }

    private fun initMainLayout() = LinearLayout(requireContext())
        .apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

    private fun initContentLayout() = LinearLayout(requireContext())
        .apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            setPadding(32, 32, 32, 32)
            addView(progressBar)
            addView(recyclerView)
            addView(errorTextView)
        }

    private fun initToolbar() = Toolbar(requireContext())
        .apply {
            title = "Beneficiaries"
            setBackgroundColor(context.getThemePrimaryColor)
        }

    private fun initRecyclerView() = RecyclerView(requireContext())
        .apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            layoutManager = LinearLayoutManager(context)
            adapter = beneficiaryAdapter
            logger.debug("RecyclerView initialized 🚀")
        }

    private fun initProgressBar() = ProgressBar(context)
        .apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { gravity = android.view.Gravity.CENTER }
            visibility = View.GONE
        }

    private fun initErrorTextView() = TextView(context)
        .apply {
            textSize = 16f
            visibility = View.GONE
            logger.debug("ErrorTextView setup to display errors 🚨")
        }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.beneficiaries.collect { state ->
                logger.debug("Collecting beneficiaries data 📦")
                progressBar.isVisible = state.isLoading
                recyclerView.isVisible = !state.isLoading && state.error == null
                errorTextView.isVisible = state.error != null

                state.error?.let {
                    errorTextView.text = it
                    logger.error("Error encountered: ${state.error} ❗")
                    Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
                }

                state.beneficiaries.let { if (it.isNotEmpty()) updateBeneficiaryAdapter(it) }
            }
        }
    }

    private fun updateBeneficiaryAdapter(beneficiaries: List<Beneficiary>) {
        (recyclerView.adapter as BeneficiaryAdapter).submitList(beneficiaries)
        logger.info("RecyclerView adapter set with ${beneficiaries.size} items 📋")
    }

    private fun showBeneficiaryDetail(beneficiary: Beneficiary) {
        val fragment = BeneficiaryDetailFragment.newInstance(beneficiary)
        (requireActivity() as MainActivity).addFragment(fragment)
        logger.info("Navigating to BeneficiaryDetailFragment for ${beneficiary.fullName}🔍")
    }

    companion object {
        fun newInstance(): BeneficiaryListFragment = BeneficiaryListFragment()
    }
}
