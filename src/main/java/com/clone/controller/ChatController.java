package com.clone.controller;

import com.clone.exception.ChatException;
import com.clone.exception.UserException;
import com.clone.model.Chat;
import com.clone.model.User;
import com.clone.request.GroupChatRequest;
import com.clone.request.SingleChatRequest;
import com.clone.response.ApiResponse;
import com.clone.service.ChatService;
import com.clone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@SuppressWarnings("unused")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest,
                                                  @RequestHeader("Authorization") String jwt) throws UserException {

        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());
        return new ResponseEntity<>(chat, HttpStatus.OK);

    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest groupChatRequest,
                                                   @RequestHeader("Authorization") String jwt) throws UserException {

        User reqUser = userService.findUserProfile(jwt);
        Chat chat = chatService.createGroup(groupChatRequest, reqUser);
        return new ResponseEntity<>(chat, HttpStatus.OK);

    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId,
                                                   @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        Chat chat = chatService.findById(chatId);
        return new ResponseEntity<>(chat, HttpStatus.OK);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfile(jwt);
        List<Chat> chats = chatService.findAllChatByUserId(user.getId());

        return new ResponseEntity<>(chats, HttpStatus.OK);

    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId,
                                                          @PathVariable Integer userId,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);
        Chat chat = chatService.addUserToGroup(userId, chatId, user);

        return new ResponseEntity<>(chat, HttpStatus.OK);

    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserFromGroupHandler(@PathVariable Integer chatId,
                                                      @PathVariable Integer userId,
                                                      @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);
        Chat chat = chatService.removeFromGroup(chatId, userId, user);

        return new ResponseEntity<>(chat, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId,
                                                                  @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);
        chatService.deleteChat(chatId, user.getId());
        ApiResponse response = new ApiResponse("Chat: DELETED SUCCESSFULLY", true);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}