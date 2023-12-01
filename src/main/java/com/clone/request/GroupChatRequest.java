package com.clone.request;

import lombok.Data;

import java.util.List;

@Data
public class GroupChatRequest {

    private List<Integer> userIds;
    private String chat_name;
    private String chat_image;

}