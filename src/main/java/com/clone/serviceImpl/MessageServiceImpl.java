package com.clone.serviceImpl;

import com.clone.exception.ChatException;
import com.clone.exception.MessageException;
import com.clone.exception.UserException;
import com.clone.model.Chat;
import com.clone.model.Message;
import com.clone.model.User;
import com.clone.repository.MessageRepository;
import com.clone.request.SendMessageRequest;
import com.clone.service.ChatService;
import com.clone.service.MessageService;
import com.clone.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final UserService userService;
    private final ChatService chatService;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(UserService userService, ChatService chatService, MessageRepository messageRepository) {
        this.userService = userService;
        this.chatService = chatService;
        this.messageRepository = messageRepository;
    }

    @Override
    public Message sendMessage(SendMessageRequest request) throws UserException, ChatException {

        User user = userService.findUserById(request.getUserId());
        Chat chat = chatService.findChatById(request.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(request.getContent());
        message.setLocalDateTime(LocalDateTime.now());

        return message;

    }

    @Override
    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {

        Chat chat = chatService.findChatById(chatId);
        if(chat.getUsers().contains(reqUser)) {
            return messageRepository.findByChatId(chat.getId());
        }
        throw new UserException("User: NOT FOUND IN THE CHAT");

    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {

        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isEmpty()) {
            throw new MessageException("Message: NOT FOUND : OPERATION FAILED");
        }
        return message.get();

    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {
        Optional<Message> message = messageRepository.findById(messageId);
        if(message.isEmpty()) {
            throw new MessageException("Message: NOT FOUND : OPERATION FAILED");
        }

        if(message.get().getUser().equals(reqUser)) {
            messageRepository.delete(message.get());
        }
        throw new UserException("User: NOT CONNECTED TO THIS CHAT : OPERATION DENIED");

    }

}