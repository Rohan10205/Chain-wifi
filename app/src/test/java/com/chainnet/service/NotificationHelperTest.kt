package com.chainnet.service

import android.app.NotificationManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NotificationHelperTest {
    @Test
    fun `creates notification channel`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val helper = NotificationHelper(context)
        helper.createChannel()

        val manager = context.getSystemService(NotificationManager::class.java)
        val channel = manager.getNotificationChannel("chainnet-service")
        assertNotNull(channel)
    }
}
