package com.example.msger.data.services.db.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

interface Database {
    val database: FirebaseDatabase
    val databaseRef: DatabaseReference

    suspend fun createChat(): Boolean

}