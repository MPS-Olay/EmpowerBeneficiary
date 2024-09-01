package com.empower.beneficiary.util

import com.empower.beneficiary.data.entity.Beneficiary
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Extension property to get the full name of a Beneficiary.
 * Combines the first and last names into a single full name string.
 */
val Beneficiary.fullName: String
    get() {
        return "$firstName $lastName"
    }

/**
 * Formats the date of birth from MMddyyyy to a more readable format, "MMMM d, yyyy".
 */
val Beneficiary.formattedDOB: String
    get() {
        val parser = SimpleDateFormat("MMddyyyy", Locale.US)
        val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.US)
        return formatter.format(parser.parse(dateOfBirth) ?: return "")
    }

/**
 * Formats the phone number to the format (XXX) XXX-XXXX if it has exactly 10 digits.
 * Returns the original phone number if it does not conform to the expected length.
 */
val Beneficiary.formattedPhoneNumber: String
    get() = with(phoneNumber) {
        if (length == 10) "(${substring(0, 3)}) ${substring(3, 6)}-${substring(6)}"
        else this
    }

/**
 * Masks the Social Security Number to only show the last 4 digits in the format ***-**-XXXX.
 * Returns the original SSN if it does not conform to the expected length.
 */
val Beneficiary.formattedSSN: String
    get() = with(socialSecurityNumber) {
        if (length == 9) "***-**-${substring(5)}"
        else this
    }
