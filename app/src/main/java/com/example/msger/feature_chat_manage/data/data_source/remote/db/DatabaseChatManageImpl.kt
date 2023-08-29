package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.core.data.data_source.remote.dto.ChatDto
import com.example.msger.core.data.data_source.remote.dto.ChatMemberDto
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DatabaseChatManageImpl : DatabaseChatManage {
    private companion object {
        const val DB_URL = "https://msger-eb05e-default-rtdb.europe-west1.firebasedatabase.app"
        const val CHATS_DB_FIELD = "chats"
        const val MEMBERS_DB_FIELD = "members"
        const val LAST_SEEN_DB_FIELD = "lastSeen"
        const val USERNAME_DB_FIELD = "username"
        const val CHAT_NAME_DB_FIELD = "chatName"
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

    override suspend fun getAllChats(): List<Map<String, ChatMemberDto>?> =
        withContext(Dispatchers.IO) {
            if (currentUserId == null) {
                listOf<Map<String, ChatMemberDto>?>()
            }
            val chats: DataSnapshot = membersRef.child(currentUserId!!).get().asDeferred().await()
            val chatList: List<Map<String, ChatMemberDto>?> = chats.children.mapNotNull {
                val chatId: String = it.key ?: ""
                val lastSeen: Long = it.child(LAST_SEEN_DB_FIELD).getValue<Long>() ?: 0
                val username: String = it.child(USERNAME_DB_FIELD).getValue<String>() ?: ""
                val chatName: String = it.child(CHAT_NAME_DB_FIELD).getValue<String>() ?: ""

                mapOf(
                    chatId to ChatMemberDto(
                        username = username,
                        lastSeen = lastSeen,
                        chatName = chatName
                    )
                )
            }
            chatList
        }


    override suspend fun addChat(chat: ChatDto, member: ChatMemberDto): String =
        withContext(Dispatchers.IO) {
            val chatId: String = chatsRef.push().key ?: ""

            if (chatId.isEmpty()) {
                throw UnsupportedOperationException("Chat id has not been generated.")
            }
            chatsRef.child(chatId).setValue(chat).await()

            addMemberChat(chatId = chatId, member = member.copy(chatName = chat.name))

            chatId
        }

    override suspend fun updateMemberChat(chatId: String, member: ChatMemberDto) {
        withContext(Dispatchers.IO) {
            if (!isChatExist(chatId = chatId)) {
                throw IllegalArgumentException("Given chat id: $chatId not exists in database")
            }
            val chat: ChatDto? = getChat(chatId = chatId)
            val chatMember: Map<String, ChatMemberDto> =
                mapOf(chatId to member.copy(chatName = chat?.name))

            membersRef
                .child(currentUserId!!)
                .updateChildren(chatMember)
                .await()
        }
    }

    override suspend fun addMemberChat(chatId: String, member: ChatMemberDto) {
        withContext(Dispatchers.IO) {
            val chat: ChatDto? = getChat(chatId = chatId)

            val chatMember: Map<String, ChatMemberDto> =
                mapOf(chatId to member.copy(chatName = chat?.name))

            membersRef
                .child(currentUserId!!)
                .updateChildren(chatMember)
                .await()
        }
    }

    override suspend fun getChat(chatId: String): ChatDto? = withContext(Dispatchers.IO) {
        val chat: DataSnapshot = chatsRef.child(chatId).get().await()

        chat.getValue<ChatDto>()
    }
}