package com.empower.beneficiary.presentation

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.empower.beneficiary.presentation.beneficiaryList.BeneficiaryListFragment

class MainActivity : AppCompatActivity() {

    private val fragmentContainer: FrameLayout by lazy {
        FrameLayout(this).apply { id = View.generateViewId() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(fragmentContainer)

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (supportFragmentManager.backStackEntryCount > 1) {
                        supportFragmentManager.popBackStack()
                    } else {
                        isEnabled = false  // Disable this callback to pass the back press to the system
                        onBackPressedDispatcher.onBackPressed()  // Invoke the default system back action
                    }
                }
            }
        )

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(fragmentContainer.id, BeneficiaryListFragment.newInstance())
                .commit()
        }
    }

    fun addFragment(fragment: Fragment) {
        // Method to replace the current fragment with a new one
        supportFragmentManager.beginTransaction()
            .add(fragmentContainer.id, fragment)
            .addToBackStack(null) // Optional: Add transaction to back stack
            .commit()
    }
}
