package com.learn.chatbot.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Chat::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract val chatDao: ChatDao

    companion object{

        private val INSTANCE: ChatDatabase? = null

        fun getInstance(context: Context):ChatDatabase{
            synchronized(this){
                var instance: ChatDatabase? = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        ChatDatabase::class.java,
                    "chat_data_database").build()
                }
                return instance
            }
        }
    }
}