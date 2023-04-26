package com.sylvainp.ca_test

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sylvainp.ca_test.adapters.secondaries.LocalBankAdapter
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.sylvainp.ca_test", appContext.packageName)
        val localBankAdapter: LocalBankAdapter = LocalBankAdapter(appContext);
        runBlocking {
            val result = localBankAdapter.getAllBanks();
            result.forEach { println("${it.name} [accounts:${it.accounts.size}]")  }
        }
    }
}