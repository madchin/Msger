package com.example.msger.feature_chat.data.data_source.db

import com.example.msger.core.data.data_source.remote.dto.ChatMemberDto
import com.example.msger.core.util.Resource
import com.example.msger.core.util.exception.GenericException
import com.example.msger.core.util.exception.UserNotAuthorizedException
import com.example.msger.feature_chat.data.data_source.dto.MessageDto
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

class RemoteDatabaseChatImpl : RemoteDatabaseChat {
    private companion object {
        const val DB_URL = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"
        const val USERNAME_DB_FIELD = "username"
        const val MEMBERS_DB_FIELD = "members"
        const val MESSAGES_DB_FIELD = "messages"
    }

    private val db: FirebaseDatabase
        get() = Firebase.database(DB_URL)

    private val membersRef: DatabaseReference
        get() = db.getReference(MEMBERS_DB_FIELD)

    private val messagesRef: DatabaseReference
        get() = db.getReference(MESSAGES_DB_FIELD)

    private val currentUserId: String?
        get() = Firebase.auth.currentUser?.uid

    override fun getChatMembers(chatId: String): Flow<Resource<List<Map<String, ChatMemberDto>?>>> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatMembers: List<Map<String, ChatMemberDto>?> = snapshot
                        .children
                        .filter { it.key == chatId }
                        .map { dataSnapshot ->
                            dataSnapshot
                                .getValue<Map<String, ChatMemberDto>>()
                        }

                    this@callbackFlow.trySend(Resource.Success(data = chatMembers))
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySend(Resource.Error(message = error.message))
                }
            }
            membersRef.addValueEventListener(listener)
            awaitClose { membersRef.removeEventListener(listener) }
        }

    override fun getChatMessages(chatId: String): Flow<Resource<List<Map<String, MessageDto>?>>> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages: List<Map<String, MessageDto>?> = snapshot.children.map {
                        it.getValue<Map<String, MessageDto>>()
                    }
                    this@callbackFlow.trySend(Resource.Success(data = messages))
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySend(Resource.Error(message = error.message))
                }
            }
            messagesRef.addValueEventListener(listener)
            awaitClose { messagesRef.removeEventListener(listener) }
        }

    override suspend fun addMessage(chatId: String, content: String) {
        if (currentUserId == null) throw UserNotAuthorizedException()
        suspend fun getMessageSender(): String =
            membersRef.child(currentUserId!!).child(chatId).child(USERNAME_DB_FIELD).get().await()
                .getValue<String>() ?: ""

        val sender: String = getMessageSender()
        val messageId: String = messagesRef.push().key ?: ""
        val message = MessageDto(content = content, sender = sender)

        try {
            messagesRef.child(chatId).child(messageId).setValue(message)
        } catch (e: Throwable) {
            throw GenericException()
        }
    }
}