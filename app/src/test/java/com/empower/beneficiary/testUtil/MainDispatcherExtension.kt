package com.empower.beneficiary.testUtil

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * A JUnit 5 extension for setting a custom [TestDispatcher] as the main coroutine dispatcher for
 * the duration of the test. This extension facilitates more controlled and predictable testing
 * of coroutine-based components that interact with the main thread, by substituting the default
 * dispatcher with a test-specific dispatcher.
 *
 * This class is marked with [ExperimentalCoroutinesApi] to indicate the experimental status of
 * coroutine testing utilities.
 *
 * ## Usage
 *
 * To use this extension in unit tests, apply it using the `@ExtendWith` annotation provided by JUnit 5:
 *
 * ```kotlin
 * @ExtendWith(MainDispatcherExtension::class)
 * class MyViewModelTest {
 *     // Test methods that involve coroutine launch on Dispatchers.Main
 * }
 * ```
 *
 * ## Parameters
 * - [testDispatcher]: The [TestDispatcher] to use as the main dispatcher for tests. Default is
 *   [UnconfinedTestDispatcher], which executes coroutines immediately on the current thread in a
 *   synchronous manner.
 *
 * ## Functions
 * - `beforeEach`: Sets the provided [testDispatcher] as the main dispatcher before each test.
 * - `afterEach`: Resets the main dispatcher to its original state after each test to avoid interference
 *   between tests or with other parts of the application.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherExtension(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : BeforeEachCallback, AfterEachCallback {

    /**
     * Sets the main coroutine dispatcher to [testDispatcher] before each test is executed.
     * This setup helps in ensuring that the tests involving Dispatchers.Main are executed
     * with a predictable and consistent dispatcher.
     *
     * @param context The context in which the current test is being executed.
     */
    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * Resets the main coroutine dispatcher to its default state after each test. This is crucial
     * to ensure that the modified main dispatcher does not affect other tests or the application
     * after test execution.
     *
     * @param context The context in which the current test has been executed.
     */
    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}
