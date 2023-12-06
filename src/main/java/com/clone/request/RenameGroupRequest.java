package com.clone.request;

import lombok.Data;

@Data
public class RenameGroupRequest {

    private Integer chatId;
    private String groupName;

}
