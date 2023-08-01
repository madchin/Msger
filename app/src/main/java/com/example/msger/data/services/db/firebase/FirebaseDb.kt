package com.example.msger.data.services.db.firebase

import com.example.msger.data.model.Chat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseDb : Database {
    private val dbUrl: String = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"
    private val db: FirebaseDatabase
        get() = Firebase.database(dbUrl)

    private val currentUserUid: String?
        get() = Firebase.auth.currentUser?.uid

    private val chatsRef: DatabaseReference
        get() = db.getReference("chats")
    private val usersRef: DatabaseReference
        get() = db.getReference("users")

    override val chats: Flow<Result<List<Chat>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chats = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Chat::class.java) ?: Chat()
                }
                this@callbackFlow.trySend(Result.success(chats))
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySend(Result.failure(error.toException()))
            }
        }
        chatsRef.addValueEventListener(listener)
        awaitClose { chatsRef.removeEventListener(listener) }
    }

    override suspend fun createChat(username: String, chat: Chat) {
        val chatId = chat.hashCode().toString()
        val chatToAdd = chat.copy(members = mutableMapOf(username to true))

        usersRef
            .child(currentUserUid!!)
            .child("chats")
            .updateChildren(mapOf(chatId to chat))

        chatsRef.child(chatId).setValue(chatToAdd).await()
    }
}