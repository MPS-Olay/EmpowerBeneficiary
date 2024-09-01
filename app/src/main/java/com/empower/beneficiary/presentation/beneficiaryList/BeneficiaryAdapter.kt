package com.empower.beneficiary.presentation.beneficiaryList

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.empower.beneficiary.data.entity.Beneficiary
import com.empower.beneficiary.util.Logger
import com.empower.beneficiary.util.fullName

class BeneficiaryAdapter(
    private val logger: Logger,
    private val beneficiaryClicked: (Beneficiary) -> Unit
) : ListAdapter<Beneficiary, BeneficiaryAdapter.ViewHolder>(BeneficiaryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LinearLayout(parent.context)
            .apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setPadding(16, 16, 16, 16)
            }
        logger.debug("Creating view holder for BeneficiaryAdapter 🌟")
        return ViewHolder(layout) { position -> beneficiaryClicked(getItem(position)) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beneficiary = getItem(position)
        holder.bind(beneficiary)
        logger.debug("Binding view holder at position $position with data: ${beneficiary.fullName} 🌟")
    }

    class ViewHolder(
        layout: LinearLayout,
        val beneficiaryClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(layout) {

        private val nameTextView: TextView = TextView(layout.context).apply { textSize = 18f }
        private val detailsTextView: TextView = TextView(layout.context).apply { textSize = 14f }

        init {
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(nameTextView)
            layout.addView(detailsTextView)
            layout.setOnClickListener { beneficiaryClicked(adapterPosition) }
        }

        fun bind(beneficiary: Beneficiary) = with(beneficiary) {
            nameTextView.text = fullName
            detailsTextView.text = String.format("%s - %s", beneType, designationCode.description)
        }
    }
}

/**
 * DiffUtil Callback for calculating the diff between two non-null items in a list.
 */
class BeneficiaryDiffCallback : DiffUtil.ItemCallback<Beneficiary>() {
    override fun areItemsTheSame(oldItem: Beneficiary, newItem: Beneficiary): Boolean {
        return oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
    }

    override fun areContentsTheSame(oldItem: Beneficiary, newItem: Beneficiary): Boolean {
        return oldItem == newItem
    }
}
