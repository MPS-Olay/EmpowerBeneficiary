package com.empower.beneficiary.data.util

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray

/**
 * Interface for managing JSON operations, specifically retrieving JSON arrays from assets.
 */
interface JsonManager {
    /**
     * Fetches a JSON array from the assets folder given a specific file name.
     *
     * @param fileName The name of the JSON file in the assets directory.
     * @return A [JSONArray] containing the data from the file.
     */
    suspend fun getJsonArrayFromAsset(fileName: String): JSONArray
}

/**
 * Provides a concrete implementation of [JsonManager] that operates on Android's asset system.
 *
 * @param context The context used to access Android assets.
 */
class AssetJsonManager(private val context: Context) : JsonManager {
    /**
     * Retrieves a JSON array from a file in the assets directory.
     *
     * @param fileName The name of the file from which to read the JSON data.
     * @return The JSON array parsed from the file contents.
     */
    override suspend fun getJsonArrayFromAsset(fileName: String): JSONArray =
        JSONArray(readTextFromAsset(fileName))

    /**
     * Reads text from an asset file.
     *
     * @param fileName The name of the file in the assets directory.
     * @return A string containing the content of the file.
     */
    private suspend fun readTextFromAsset(fileName: String): String = withContext(Dispatchers.IO) {
        context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}
