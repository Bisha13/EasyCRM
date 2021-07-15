package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Part;
import ru.bisha.easycrm.db.repository.PartRepository;
import ru.bisha.easycrm.service.PartService;

import java.util.List;

@Service
public class PartServiceImp implements PartService {

    @Autowired
    private PartRepository repository;

    @Override
    public void deletePart(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<Part> getAll() {
        return repository.findAll();
    }
}
