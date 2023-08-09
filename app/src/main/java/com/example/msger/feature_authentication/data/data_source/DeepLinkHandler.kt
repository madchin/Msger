package com.example.msger.feature_authentication.data.data_source

import android.net.Uri

interface DeepLinkHandler {
    suspend fun getDynamicLink(): Uri?
}