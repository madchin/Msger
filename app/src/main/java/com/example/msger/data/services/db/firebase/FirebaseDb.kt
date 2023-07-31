package com.example.msger.data.services.db.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseDb : Database {
    private val dbUrl: String = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"
    override val database: FirebaseDatabase
        get() = FirebaseDatabase.getInstance()

    override val databaseRef: DatabaseReference
        get() = database.getReference(dbUrl)

    override suspend fun createChat(): Boolean {
        return true
    }
}