package com.trustmubaiwa.dvtweatherproject.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LocationUtilsTest : TestCase() {

    private lateinit var locationUtils: LocationUtils

    @Mock
    private lateinit var mockDataStore: DataStore<Preferences>

    @Mock
    private lateinit var mockContext: Context

    @Before
    override fun setUp() {
        super.setUp()
        MockitoAnnotations.initMocks(this)

        runBlocking {
            locationUtils = LocationUtils(mockDataStore, mockContext)
        }
    }

    @Test
    fun `test true`() {
        assertTrue(true)
    }
}