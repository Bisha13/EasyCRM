package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.StatusEntity;
import ru.bisha.easycrm.db.repository.StatusRepository;
import ru.bisha.easycrm.service.StatusService;
import java.util.List;

@Service
public class StatusServiceImp implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<StatusEntity> getAll() {
        return statusRepository.findAll();
    }

    @Override
    public StatusEntity findById(long id) {
        return statusRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(StatusEntity status) {
        statusRepository.save(status);
    }
}
