package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.ChatDto
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.MemberDto
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.UserChat
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

class RemoteDatabaseChatManageImpl : RemoteDatabaseChatManage {
    private companion object {
        const val DB_URL = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"
        const val CHATS_DB_FIELD = "chats"
        const val MEMBERS_DB_FIELD = "members"
    }

    private val db: FirebaseDatabase
        get() = Firebase.database(DB_URL)

    private val chatsRef: DatabaseReference
        get() = db.getReference(CHATS_DB_FIELD)

    private val membersRef: DatabaseReference
        get() = db.getReference(MEMBERS_DB_FIELD)

    private suspend fun isChatExist(chatId: String): Boolean =
        chatsRef.get().await().hasChild(chatId)

    override val currentUserId: String?
        get() = Firebase.auth.currentUser?.uid

    override fun getAllChats(): Flow<Resource<List<Map<String, UserChat>?>>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUserChats = snapshot
                    .children
                    .filter { it.key == currentUserId }
                    .map { dataSnapshot ->
                        dataSnapshot.getValue<Map<String, UserChat>>()
                    }

                this@callbackFlow.trySend(Resource.Success(data = currentUserChats))
            }

            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySend(Resource.Error(message = error.message))
            }

        }
        membersRef.addValueEventListener(listener)
        awaitClose { membersRef.removeEventListener(listener) }
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