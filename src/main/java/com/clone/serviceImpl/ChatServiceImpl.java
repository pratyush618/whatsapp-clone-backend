package com.clone.serviceImpl;

import com.clone.exception.ChatException;
import com.clone.exception.UserException;
import com.clone.model.Chat;
import com.clone.model.User;
import com.clone.repository.ChatRepository;
import com.clone.request.GroupChatRequest;
import com.clone.service.ChatService;
import com.clone.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public Chat createChat(User reqUser, Integer sendId) throws UserException {

        User user = userService.findUserById(sendId);
        Chat isChatExist = chatRepository.findSingleChatByUserIds(reqUser, user);

        if(isChatExist != null) {
            return isChatExist;
        }

        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(reqUser);
        chat.getUsers().add(user);
        chat.setIsGroup(false);

        return null;

    }

    @Override
    public Chat findById(Integer chatId) throws ChatException {
        return null;
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer id) throws UserException {
        return null;
    }

    @Override
    public Chat createGroup(GroupChatRequest request, Integer reqUserId) throws UserException {
        return null;
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId) throws UserException, ChatException {
        return null;
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, Integer reqUserId) throws UserException, ChatException {
        return null;
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, Integer reqUser) throws UserException, ChatException {
        return null;
    }

    @Override
    public Chat deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        return null;
    }

}
