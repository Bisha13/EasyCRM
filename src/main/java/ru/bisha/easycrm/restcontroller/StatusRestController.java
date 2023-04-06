package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.db.entity.Status;
import ru.bisha.easycrm.db.repository.StatusRepository;

import java.util.List;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class StatusRestController {

    private final StatusRepository statusRepository;

    @GetMapping("/all")
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }
}
