package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Status;
import ru.bisha.easycrm.db.repository.StatusRepository;
import ru.bisha.easycrm.service.StatusService;
import java.util.List;

@Service
public class StatusServiceImp implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    @Override
    public Status findById(long id) {
        return statusRepository.findById(id).orElseThrow();
    }
}
