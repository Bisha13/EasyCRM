package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.StatusEntity;
import ru.bisha.easycrm.db.repository.StatusRepository;

import java.util.List;

@RestController
@RequestMapping("/rest/status")
@RequiredArgsConstructor
public class StatusesController {

    private final StatusRepository statusRepository;

    @GetMapping("/all")
    public List<StatusEntity> getAllStatuses() {
        return statusRepository.findAll();
    }

    @PostMapping
    public void updateStatuses(@RequestBody List<StatusEntity> statuses) {
        statusRepository.saveAll(statuses);
    }

}
