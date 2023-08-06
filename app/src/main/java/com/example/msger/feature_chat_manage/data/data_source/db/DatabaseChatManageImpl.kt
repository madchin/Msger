package com.example.msger.feature_chat_manage.data.data_source.db

import com.example.msger.feature_chat_manage.data.data_source.dto.MemberDto
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat.domain.model.Member
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

class DatabaseChatManageImpl : DatabaseChatManage {
    private val dbUrl: String = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"
    private val db: FirebaseDatabase
        get() = Firebase.database(dbUrl)

    private val chatsRef: DatabaseReference
        get() = db.getReference("chats")

    private val membersRef: DatabaseReference
        get() = db.getReference("members")

    override val currentUserId: String?
        get() = Firebase.auth.currentUser?.uid

    override val chats: Flow<Result<List<Chat>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatEntities = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(Chat::class.java) ?: Chat()
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

    override suspend fun createChat(username: String, chat: Chat): String {
        val chatId: String = chatsRef.push().key ?: ""
        val chatMember = MemberDto(
            member = mapOf(
                currentUserId to Member(
                    lastSeen = chat.created,
                    name = username
                )
            )
        )

        chatsRef.child(chatId).setValue(chat).await()

        membersRef
            .child(chatId)
            .updateChildren(chatMember.member)
            .await()

        return chatId
    }
}