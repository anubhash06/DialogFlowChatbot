package com.learn.chatbot

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.chatbot.databinding.ActivityMainBinding
import com.learn.chatbot.db.ChatDatabase
import com.learn.chatbot.db.ChatRepository

class MainActivity : AppCompatActivity() {

    val TAG: String? = MainActivity::class.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = ChatDatabase.getInstance(application).chatDao
        val repository = ChatRepository(dao)
        val factory = ChatViewModelFactory(applicationContext, repository)
        chatViewModel = ViewModelProvider(this, factory).get(ChatViewModel::class.java)
        binding.myViewModel = chatViewModel
        binding.lifecycleOwner = this
        initRecyclerView()
        initSuggestionRecycleView()
    }

    private fun initSuggestionRecycleView() {
        binding.suggestionRecyclerView.layoutManager = LinearLayoutManager(
            applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        val iconsName = listOf(
            "Hello",
            "How are you ?",
            "What is your name ?",
            "What do you do ?",
            "Where do you live ?"
        )

        binding.suggestionRecyclerView.adapter = SuggestionRecyclerViewAdapter(iconsName, {selectedItem: String->onListItemClicked(selectedItem)})
    }

    private fun onListItemClicked(item: String){
        chatViewModel.sendMsg(item)
    }
    private fun initRecyclerView() {
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd=true
        binding.chatRecyclerView.layoutManager = linearLayoutManager
        displayChatList()
    }

    private fun displayChatList() {
        chatViewModel.chats.observe(this, Observer {
            Log.d(TAG, it.toString())
            binding.chatRecyclerView.adapter = ChatRecyclerViewAdapter(it)
        })
    }
}