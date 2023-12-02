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
import java.util.Optional;

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

        return chat;

    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {

        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isEmpty()) {
            throw new ChatException("Chat not found with ID: " + chatId);
        }
        return chat.get();

    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        return chatRepository.findChatByUserId(user.getId());
    }

    @Override
    public Chat createGroup(GroupChatRequest request, User reqUser) throws UserException {

        Chat group = new Chat();
        group.setIsGroup(true);
        group.setChat_name(request.getChat_name());
        group.setChat_image(request.getChat_image());
        group.setCreatedBy(reqUser);
        group.getAdmins().add(reqUser);

        for(Integer userId : request.getUserIds()) {
            User user = userService.findUserById(userId);
            group.getUsers().add(user);
        }

        return group;

    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {

        Optional<Chat> chat = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if(chat.isEmpty()) {
            throw new ChatException("Chat not found with ID: " + chatId);
        }

        if(chat.get().getAdmins().contains(reqUser)) {
            chatRepository.save(chat.get());
            return chat.get();
        } else {
            throw new UserException("User: NOT ADMIN : OPERATION DENIED");
        }

    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws UserException, ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isEmpty()) {
            throw new ChatException("Chat (" + chatId + "): NOT FOUND");
        }
        if(chat.get().getAdmins().contains(reqUser)) {
            chat.get().setChat_name(groupName);
            chatRepository.save(chat.get());
            return chat.get();
        }

        throw new UserException("User: NOT ADMIN : OPERATION DENIED");
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {

        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isEmpty()) {
            throw new ChatException("Chat (" + chatId + "): NOT FOUND");
        }

        if(chat.get().getAdmins().contains(reqUser)) {
            Optional<User> user = Optional.ofNullable(userService.findUserById(userId));
            if(user.isEmpty()) {
                throw new UserException("User: NOT FOUND IN THE GROUP : OPERATION FAILED");
            }
            chat.get().getUsers().remove(user.get());
            chatRepository.save(chat.get());
        }
        throw new UserException("User: NOT ADMIN : OPERATION DENIED");

    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {

        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isEmpty()) {
            throw new ChatException("Chat (" + chatId + "): NOT FOUND");
        }

        Optional<User> user = Optional.ofNullable(userService.findUserById(userId));
        if(user.isEmpty()) {
            throw new UserException("User: NOT FOUND IN THE GROUP : OPERATION FAILED");
        }
        chatRepository.deleteById(chat.get().getId());

    }

}