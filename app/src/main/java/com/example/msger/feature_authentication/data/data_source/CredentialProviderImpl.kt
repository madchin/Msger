package com.example.msger.feature_authentication.data.data_source

import androidx.core.net.toUri
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CredentialProviderImpl : CredentialProvider {
    override suspend fun getUserEmailAfterPasswordRecover(): String {
        val firebaseDynamicLink = "mesager.page.link"
        val uri = firebaseDynamicLink.toUri()
        val dynamicLink = Firebase.dynamicLinks.getDynamicLink(uri).await()
        val dynamicLinkData = dynamicLink.link

        return dynamicLinkData?.getQueryParameter("email") ?: ""
    }
}