package com.clone.service;

import com.clone.exception.StatusException;
import com.clone.model.Status;
import java.util.List;

public interface StatusService {

    Status createStatus(Status status) throws StatusException;

    List<Status> getAllStatusByUserId(Integer userId);

    void deleteStatus(Integer statusId) throws StatusException;

}
