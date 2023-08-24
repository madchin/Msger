package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.core.data.data_source.remote.dto.ChatDto
import com.example.msger.core.data.data_source.remote.dto.MemberDto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class RemoteDatabaseChatManageImpl : RemoteDatabaseChatManage {
    private companion object {
        const val DB_URL = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"
        const val CHATS_DB_FIELD = "chats"
        const val MEMBERS_DB_FIELD = "members"
        const val NAME_DB_FIELD = "name"
        const val LAST_SEEN_DB_FIELD = "lastSeen"
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

    override suspend fun getAllChats(): List<HashMap<String, MemberDto>?> {
        if (currentUserId == null) {
            return listOf()
        }

        val chatsRequest = membersRef.child(currentUserId!!).get().await()

        val chatList: List<HashMap<String,MemberDto>?> = chatsRequest.children.mapNotNull {
            val chatId: String = it.key ?: ""
            val lastSeen: Long = it.child(LAST_SEEN_DB_FIELD).getValue<Long>() ?: 0
            val username: String = it.child(NAME_DB_FIELD).getValue<String>() ?: ""

            hashMapOf(chatId to MemberDto(name = username, lastSeen = lastSeen))
        }
        return chatList
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