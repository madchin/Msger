package com.example.msger.data.services.db.firebase

import com.example.msger.data.model.db.ChatEntity
import com.example.msger.data.model.db.MemberEntity
import com.google.firebase.auth.ktx.auth
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
import kotlinx.coroutines.tasks.await

class FirebaseDb : Database {
    private val dbUrl: String = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"
    private val db: FirebaseDatabase
        get() = Firebase.database(dbUrl)

    private val chatsRef: DatabaseReference
        get() = db.getReference("chats")
    private val membersRef: DatabaseReference
        get() = db.getReference("members")

    override val currentUserId: String?
        get() = Firebase.auth.currentUser?.uid

    override val chats: Flow<Result<List<ChatEntity>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatEntities = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(ChatEntity::class.java) ?: ChatEntity()
                }
                this@callbackFlow.trySend(Result.success(chatEntities))
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySend(Result.failure(error.toException()))
            }
        }
        chatsRef.addValueEventListener(listener)
        awaitClose { chatsRef.removeEventListener(listener) }
    }

    override fun getChatMembers(chatId: String): Flow<Result<List<Map<String, MemberEntity>?>>> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val memberEntities = snapshot
                        .children
                        .filter { it.key == chatId }
                        .map { dataSnapshot ->
                            dataSnapshot
                                .getValue<Map<String, MemberEntity>>()
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

    override suspend fun createChat(username: String, chatEntity: ChatEntity): String {
        val chatId = chatsRef.push().key ?: ""
        val chatMemberEntity =
            mapOf(currentUserId to MemberEntity(lastSeen = chatEntity.created, name = username))

        chatsRef.child(chatId).setValue(chatEntity).await()

        membersRef
            .child(chatId)
            .updateChildren(chatMemberEntity)
            .await()

        return chatId
    }
}