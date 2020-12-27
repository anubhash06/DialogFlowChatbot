package com.learn.chatbot

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.learn.chatbot.db.ChatRepository
import java.lang.IllegalArgumentException

class ChatViewModelFactory(private val applicationContext: Context, private val repo: ChatRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatViewModel::class.java)){
            return ChatViewModel(applicationContext, repo) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}