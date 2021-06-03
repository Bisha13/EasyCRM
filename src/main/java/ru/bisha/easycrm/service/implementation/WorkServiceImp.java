package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.repository.WorkRepository;
import ru.bisha.easycrm.service.WorkService;

@Service
public class WorkServiceImp implements WorkService {

    @Autowired
    WorkRepository workRepository;

    @Override
    public void deleteWork(int id) {
        workRepository.deleteById(id);
    }
}
