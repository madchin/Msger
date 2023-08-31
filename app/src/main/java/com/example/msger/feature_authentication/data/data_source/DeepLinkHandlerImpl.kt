package com.example.msger.feature_authentication.data.data_source

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DeepLinkHandlerImpl : DeepLinkHandler {
    override suspend fun getDynamicLink(): Uri? {
        val uri: Uri = FIREBASE_DYNAMIC_LINK.toUri()
        val dynamicLink: PendingDynamicLinkData = withContext(Dispatchers.IO) { Firebase.dynamicLinks.getDynamicLink(uri).await() }

        return dynamicLink.link
    }
    companion object {
        private const val FIREBASE_DYNAMIC_LINK = "mesager.page.link"
    }
}

