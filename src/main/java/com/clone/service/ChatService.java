package com.clone.service;

import com.clone.exception.ChatException;
import com.clone.exception.UserException;
import com.clone.model.Chat;
import com.clone.model.User;
import com.clone.request.GroupChatRequest;

import java.util.List;

public interface ChatService {

    Chat createChat(User reqUser, Integer sender) throws UserException;
    Chat findById(Integer chatId) throws ChatException;
    List<Chat> findAllChatByUserId(Integer userId) throws UserException;
    Chat createGroup(GroupChatRequest request, User reqUser) throws UserException;
    Chat addUserToGroup(Integer userId, Integer chatId) throws UserException, ChatException;
    Chat renameGroup(Integer chatId, String groupName, Integer reqUserId) throws UserException, ChatException;
    Chat removeFromGroup(Integer chatId, Integer userId, Integer reqUser) throws UserException, ChatException;
    Chat deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;

}