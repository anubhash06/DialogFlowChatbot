package com.learn.chatbot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.learn.chatbot.databinding.ListItemBinding
import com.learn.chatbot.db.Chat

class ChatRecyclerViewAdapter(private val chatList: List<Chat>):
    RecyclerView.Adapter<ChatViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
            return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}

class ChatViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(chat: Chat){
        binding.agentTextView.text = "${chat.agentResponse} "
        binding.yourTextView.text = "${chat.userMsg}"
    }
}