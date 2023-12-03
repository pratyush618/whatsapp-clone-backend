package com.clone.controller;

import com.clone.exception.ChatException;
import com.clone.exception.MessageException;
import com.clone.exception.UserException;
import com.clone.model.Message;
import com.clone.model.User;
import com.clone.request.SendMessageRequest;
import com.clone.response.ApiResponse;
import com.clone.service.MessageService;
import com.clone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest request,
                                                      @RequestHeader("Authorization") String jwt)
            throws ChatException, UserException {

        User user = userService.findUserProfile(jwt);
        request.setUserId(user.getId());
        Message message = messageService.sendMessage(request);

        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatsMessagesHandler(@PathVariable("chatId") Integer chatId,
                                                                 @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);
        List<Message> messages = messageService.getChatsMessages(chatId, user);

        return new ResponseEntity<>(messages, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable("messageId") Integer messageId,
                                                            @RequestHeader("Authorization") String jwt)
            throws UserException, MessageException {

        User user = userService.findUserProfile(jwt);
        messageService.deleteMessage(messageId, user);

        ApiResponse apiResponse = new ApiResponse("Message: DELETED SUCCESSFULLY", true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}