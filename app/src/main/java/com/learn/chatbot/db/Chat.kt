package com.learn.chatbot.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_data_table")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_id")
    val id: Int,

    @ColumnInfo(name = "agent_response")
    val agentResponse: String,

    @ColumnInfo(name = "user_msg")
    val userMsg: String
) {
}