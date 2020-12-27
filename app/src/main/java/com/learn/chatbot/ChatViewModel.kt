package com.learn.chatbot

import ai.api.AIServiceException
import ai.api.android.AIConfiguration
import ai.api.android.AIService
import ai.api.model.AIRequest
import ai.api.model.AIResponse
import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.chatbot.db.Chat
import com.learn.chatbot.db.ChatRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatViewModel(private val applicationContext: Context, private val repo: ChatRepository) :
    ViewModel(), Observable {


    val chats = repo.chat

    @Bindable
    val inputText = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()


    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()


    init {
        saveOrUpdateButtonText.value = "Send"
        clearAllOrDeleteButtonText.value = "Clear Chat"
    }

    fun saveOrUpdate() {

        val config = AIConfiguration(
            "63058a6473684d349ff9dffa98d8f31d",
            ai.api.AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System
        )
        val aiService = AIService.getService(applicationContext, config)
        val aiRequest = AIRequest()
        aiRequest.setQuery(inputText.value!!)
        try {
            var response: AIResponse? = null
            Thread {
                response = aiService.textRequest(aiRequest)
                val name: String = inputText.value!!
                viewModelScope.launch {
                    insert(Chat(0, response!!.getResult().getFulfillment().getSpeech(), name))
                    inputText.value = null
                }
            }.start()
        } catch (e: AIServiceException) {
            e.printStackTrace()
        }
    }

    fun clearAllOrDelete() {
        clearAll()
    }

    fun insert(chat: Chat): Job =
        viewModelScope.launch {
            repo.insert(chat)
        }

    /**
     * we can uncomment below methods to update or delete a perticular chat
     */
    /*  fun update(chat: Chat): Job = viewModelScope.launch { repo.update(chat) }

      fun delete(chat: Chat): Job = viewModelScope.launch { repo.delete(chat) }*/

    fun clearAll(): Job = viewModelScope.launch { repo.deleteAll() }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}