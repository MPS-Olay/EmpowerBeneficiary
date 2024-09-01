package com.empower.beneficiary.util

import android.util.Log

/**
 * A Logger interface for standardized logging throughout an application.
 * Provides methods for logging at different levels including debug, information, and error.
 */
interface Logger {
    /**
     * Logs a debug message.
     * @param message The message to log.
     */
    fun debug(message: String)

    /**
     * Logs an informational message.
     * @param message The message to log.
     */
    fun info(message: String)

    /**
     * Logs an error message.
     * @param message The message to log.
     */
    fun error(message: String)

    /**
     * Logs an error message along with a throwable.
     * @param message The message to log.
     * @param throwable The throwable to log.
     */
    fun error(message: String, throwable: Throwable)

    /**
     * Returns a new instance of the Logger with a specified tag.
     * @param tag The tag for the new logger instance.
     * @return A new instance of Logger with the provided tag.
     */
    fun withTag(tag: String): Logger
}

class SimpleLogger(private val tag: String) : Logger {
    override fun debug(message: String) {
        Log.d(tag, message)
    }

    override fun info(message: String) {
        Log.i(tag, message)
    }

    override fun error(message: String) {
        Log.e(tag, message)
    }

    override fun error(message: String, throwable: Throwable) {
        Log.e(tag, message, throwable)
    }

    override fun withTag(tag: String): Logger = SimpleLogger(tag)
}
