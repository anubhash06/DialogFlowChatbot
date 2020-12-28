package com.learn.chatbot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.learn.chatbot.databinding.ListItemBinding
import com.learn.chatbot.databinding.ListItemSuggestionBinding
import com.learn.chatbot.db.Chat

class SuggestionRecyclerViewAdapter(private val chatList: List<String>, private val clickListener:(String)-> Unit):
    RecyclerView.Adapter<SuggestionViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemSuggestionBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item_suggestion, parent, false)
        return SuggestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bind(chatList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
}

class SuggestionViewHolder(val binding: ListItemSuggestionBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: String, clickListener:(String)-> Unit){
        binding.suggestionTextView.text = "${item} "
        binding.cardView.setOnClickListener{
            clickListener(item)
        }

    }
}