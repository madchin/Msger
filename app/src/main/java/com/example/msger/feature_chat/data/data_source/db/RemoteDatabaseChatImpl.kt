package com.example.msger.feature_chat.data.data_source.db

import com.example.msger.core.data.data_source.remote.dto.ChatMemberDto
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RemoteDatabaseChatImpl : RemoteDatabaseChat {

    private val dbUrl: String = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"

    private val db: FirebaseDatabase
        get() = Firebase.database(dbUrl)

    private val membersRef: DatabaseReference
        get() = db.getReference("members")

    override fun getChatMembers(chatId: String): Flow<Result<List<Map<String, ChatMemberDto>?>>> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val memberEntities = snapshot
                        .children
                        .filter { it.key == chatId }
                        .map { dataSnapshot ->
                            dataSnapshot
                                .getValue<Map<String, ChatMemberDto>>()
                        }

                    this@callbackFlow.trySend(Result.success(memberEntities))
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySend(Result.failure(error.toException()))
                }
            }
            membersRef.addValueEventListener(listener)
            awaitClose { membersRef.removeEventListener(listener) }
        }
}