package com.learn.chatbot.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: Chat)

    /**
     * we can uncomment below methods to update or delete a perticular chat
     */
/*
    @Update
    suspend fun updateChat(chat: Chat)

    @Delete
    suspend fun deleteChat(chat: Chat)
*/

    @Query("DELETE FROM chat_data_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM chat_data_table")
    fun getAllChatData(): LiveData<List<Chat>>
}