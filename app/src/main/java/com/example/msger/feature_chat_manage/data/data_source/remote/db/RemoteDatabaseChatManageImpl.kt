package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.feature_chat_manage.data.data_source.remote.dto.ChatDto
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.MemberDto
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

class RemoteDatabaseChatManageImpl : RemoteDatabaseChatManage {
    private val dbUrl: String = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"
    private val db: FirebaseDatabase
        get() = Firebase.database(dbUrl)

    private val chatsRef: DatabaseReference
        get() = db.getReference("chats")

    private val membersRef: DatabaseReference
        get() = db.getReference("members")

    private suspend fun isChatExist(chatId: String): Boolean =
        chatsRef.get().await().hasChild(chatId)

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

    override suspend fun getChats(): List<Map<String,ChatDto>?> {
        val dataSnapshot: DataSnapshot = membersRef.child(currentUserId!!).get().await()

        return dataSnapshot
            .children
            .flatMap {
                it.children.map { chat ->
                    chat.getValue(mapOf<String, ChatDto>()::class.java)
                }
            }
    }

    override suspend fun addChat(chat: ChatDto, member: MemberDto): String {
        val chatId: String = chatsRef.push().key ?: ""

        if (chatId.isEmpty()) {
            throw UnsupportedOperationException("Chat id has not been generated.")
        }

        chatsRef.child(chatId).setValue(chat).await()

        addMemberChat(chatId = chatId, member = member)

        return chatId
    }

    override suspend fun updateMemberChat(chatId: String, member: MemberDto) {
        if (!isChatExist(chatId = chatId)) {
            throw IllegalArgumentException("Given chat id: $chatId not exists in database")
        }
        val chatMember: Map<String, MemberDto> = mapOf(chatId to member)

        membersRef
            .child(currentUserId!!)
            .updateChildren(chatMember)
            .await()
    }

    override suspend fun addMemberChat(chatId: String, member: MemberDto) {
        val chatMember: Map<String, MemberDto> = mapOf(chatId to member)

        membersRef
            .child(currentUserId!!)
            .updateChildren(chatMember)
            .await()
    }
}