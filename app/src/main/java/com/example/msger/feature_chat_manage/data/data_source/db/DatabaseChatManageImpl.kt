package com.example.msger.feature_chat_manage.data.data_source.db

import com.example.msger.feature_chat_manage.data.data_source.dto.ChatDto
import com.example.msger.feature_chat_manage.data.data_source.dto.MemberDto
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

    override val chats: Flow<Result<List<ChatDto>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatEntities = snapshot.children.map { dataSnapshot ->
                    dataSnapshot.getValue(ChatDto::class.java) ?: ChatDto()
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

    override suspend fun addChat(chat: ChatDto): String {
        val chatId: String = chatsRef.push().key ?: ""

        if (chatId.isEmpty()) {
            throw UnsupportedOperationException("Chat id has not been generated.")
        }

        chatsRef.child(chatId).setValue(chat).await()

        return chatId
    }

    override suspend fun updateMemberChats(chatId: String, member: MemberDto) {
        if (chatsRef.key != chatId) {
            throw IllegalArgumentException("Given chat id: $chatId not exists in database")
        }
        val chatMember: Map<String?, MemberDto> = mapOf(chatId to member)

        membersRef
            .child(currentUserId!!)
            .updateChildren(chatMember)
            .await()
    }
}