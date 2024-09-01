package com.empower.beneficiary.presentation.beneficiaryDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.empower.beneficiary.R
import com.empower.beneficiary.data.entity.Beneficiary
import com.empower.beneficiary.util.formattedDOB
import com.empower.beneficiary.util.formattedPhoneNumber
import com.empower.beneficiary.util.formattedSSN
import com.empower.beneficiary.util.fullName
import com.empower.beneficiary.util.getThemeBackgroundColor
import com.empower.beneficiary.util.getThemePrimaryColor

class BeneficiaryDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainLayout = initMainLayout()
        val toolbar = initToolbar()
        val contentLayout = initContentLayout()

        mainLayout.apply {
            addView(toolbar)
            addView(contentLayout)
        }

        arguments?.getParcelable<Beneficiary>(ARG_BENEFICIARY)?.let { beneficiary ->
            setupContent(contentLayout, beneficiary)
            toolbar.title = beneficiary.fullName
        }

        return mainLayout
    }

    private fun initMainLayout() = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        setBackgroundColor(context.getThemeBackgroundColor)
        isClickable = true
    }

    private fun initContentLayout() = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        setPadding(32, 32, 32, 32)
    }

    private fun initToolbar() = Toolbar(requireContext()).apply {
        title = "Detail"
        navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_back_arrow)
        setBackgroundColor(context.getThemePrimaryColor)
        setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
    }

    private fun setupContent(contentLayout: LinearLayout, beneficiary: Beneficiary) {
        with(beneficiary) {
            contentLayout.addView(createTextView("Name: $fullName"))
            contentLayout.addView(createTextView("SSN: $formattedSSN"))
            contentLayout.addView(createTextView("Date of Birth: $formattedDOB"))
            contentLayout.addView(createTextView("Phone Number: $formattedPhoneNumber"))
            contentLayout.addView(createTextView("Address: ${beneficiaryAddress.firstLineMailing}, ${beneficiaryAddress.city}"))
        }
    }

    private fun createTextView(text: String) = TextView(context).apply {
        this.text = text
        textSize = 18f
        setPadding(8, 8, 8, 8)
    }

    companion object {
        private const val ARG_BENEFICIARY = "beneficiary"

        /**
         * Creates a new instance of BeneficiaryDetailFragment with the given beneficiary.
         */
        fun newInstance(
            beneficiary: Beneficiary
        ): BeneficiaryDetailFragment = BeneficiaryDetailFragment()
            .apply { arguments = bundleOf(ARG_BENEFICIARY to beneficiary) }
    }
}
