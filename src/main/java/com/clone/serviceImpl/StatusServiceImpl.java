package com.clone.serviceImpl;

import com.clone.exception.StatusException;
import com.clone.model.Status;
import com.clone.repository.StatusRepository;
import com.clone.service.StatusService;

import java.util.List;
import java.util.Optional;

public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status createStatus(Status status) throws StatusException {
        if(statusRepository.existsById(status.getId())) {
            throw new StatusException("ERROR: Status already present. Duplicacy prohibited");
        }

        return statusRepository.save(status);
    }

    @Override
    public List<Status> getAllStatusByUserId(Integer userId) {
        return null;
    }

    @Override
    public void deleteStatus(Integer statusId) throws StatusException {
        Optional<Status> status = statusRepository.findById(statusId);
        if(status.isEmpty()) {
            throw new StatusException("Status: NOT FOUND");
        }

        statusRepository.delete(status.get());
    }

}
