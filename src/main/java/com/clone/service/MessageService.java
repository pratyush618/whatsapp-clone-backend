package com.clone.service;

import com.clone.exception.ChatException;
import com.clone.exception.MessageException;
import com.clone.exception.UserException;
import com.clone.model.Message;
import com.clone.model.User;
import com.clone.request.SendMessageRequest;

import java.util.List;

public interface MessageService {

    Message sendMessage(SendMessageRequest request) throws UserException, ChatException;
    List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException;
    Message findMessageById(Integer messageId) throws MessageException;
    void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;

}