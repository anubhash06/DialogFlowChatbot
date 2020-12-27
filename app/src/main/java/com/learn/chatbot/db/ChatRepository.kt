package com.learn.chatbot.db

class ChatRepository(private val dao: ChatDao) {

    val chat = dao.getAllChatData()

    suspend fun insert(chat: Chat){
        dao.insertChat(chat)
    }


    /**
     * we can uncomment below methods to update or delete a perticular chat
     */
  /*  suspend fun update(chat: Chat){
        dao.updateChat(chat)
    }

    suspend fun delete(chat: Chat){
        dao.deleteAll()
    }
*/
    suspend fun deleteAll(){
        dao.deleteAll()
    }

}