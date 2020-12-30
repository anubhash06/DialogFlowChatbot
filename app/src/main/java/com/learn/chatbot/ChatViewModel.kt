package com.learn.chatbot

import ai.api.AIServiceException
import ai.api.android.AIConfiguration
import ai.api.android.AIService
import ai.api.model.AIRequest
import ai.api.model.AIResponse
import android.content.Context
import android.text.TextUtils
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learn.chatbot.db.Chat
import com.learn.chatbot.db.ChatRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.IOException


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

   /* @Throws(IOException::class)
    fun authExplicit(jsonPath: String?) {
        // You can specify a credential file by providing a path to GoogleCredentials.
        // Otherwise credentials are read from the GOOGLE_APPLICATION_CREDENTIALS environment variable.
        val credentials: GoogleCredentials = GoogleCredentials.fromStream(FileInputStream(jsonPath))
            .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"))
        val storage: Storage =
            StorageOptions.newBuilder().setCredentials(credentials).build().getService()
        println("Buckets:")
        val buckets: Page<Bucket> = storage.list()
        for (bucket in buckets.iterateAll()) {
            System.out.println(bucket.toString())
        }
    }*/

    fun sendMsg(msg: String){
        inputText.value= msg
        saveOrUpdate()
    }
    fun saveOrUpdate() {

        if(TextUtils.isEmpty(inputText.value))
            return;
        val config = AIConfiguration(
            "63058a6473684d349ff9dffa98d8f31d",
            ai.api.AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System
        )

        val aiService = AIService.getService(applicationContext, config)
//        val aiDataService = AIDataService(applicationContext, config)
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