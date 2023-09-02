package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.core.data.data_source.remote.dto.ChatDto
import com.example.msger.core.data.data_source.remote.dto.ChatMemberDto
import com.example.msger.core.util.exception.UserNotAuthorizedException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
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

    override val currentUserId: String?
        get() = Firebase.auth.currentUser?.uid

    override suspend fun getAllChats(): List<Map<String, ChatMemberDto>?> =
        withContext(Dispatchers.IO) {
            if (currentUserId == null) {
                return@withContext emptyList()
            }
            val chats: DataSnapshot =
                membersRef.child(currentUserId ?: "").get().await()
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
            val chatId: String = chatsRef.push().key
                ?: throw UnsupportedOperationException("Chat id has not been generated.")

            if (currentUserId == null) {
                throw UserNotAuthorizedException()
            }

            chatsRef
                .child(chatId)
                .setValue(chat.copy(members = listOf(currentUserId!!)))
                .await()

            chatId
        }

    override suspend fun updateMember(chatId: String, member: ChatMemberDto): ChatDto =
        withContext(Dispatchers.IO) {

            val chat: ChatDto = getChat(chatId = chatId)
            val chatMember: Map<String, ChatMemberDto> =
                mapOf(chatId to member.copy(chatName = chat.name))

            membersRef
                .child(currentUserId!!)
                .updateChildren(chatMember)
                .await()
            return@withContext chat.copy(chatId = chatId)
        }


    override suspend fun addMember(chatId: String, member: ChatMemberDto) {
        withContext(Dispatchers.IO) {
            val chat: ChatDto = getChat(chatId = chatId)

            val chatMember: ChatMemberDto = member.copy(chatName = chat.name)

            membersRef
                .child(currentUserId!!)
                .child(chatId)
                .setValue(chatMember)
                .await()
        }
    }

    override suspend fun getChat(chatId: String): ChatDto = withContext(Dispatchers.IO) {
        val chat: DataSnapshot = chatsRef.child(chatId).get().await()

        chat.getValue<ChatDto>() ?: throw NoSuchFieldException("Chat doesn't exists")
    }

    override suspend fun updateChat(chat: ChatDto) {
        val chatId: String = chat.chatId ?: ""
        val chatMembers: List<String> =
            if (chat.members.contains(currentUserId)) chat.members else chat.members + currentUserId!!

        chatsRef.updateChildren(mapOf(chatId to chat.copy(members = chatMembers, chatId = null)))
            .await()
    }
}