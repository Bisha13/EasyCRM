package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.repository.ServiceRepository;
import ru.bisha.easycrm.dto.UpdateServiceStatusDto;

@RestController
@RequestMapping("/rest/services")
@RequiredArgsConstructor
public class ServicesController {

    private final ServiceRepository serviceRepository;

    @DeleteMapping ("/{id}")
    public void deleteService(@PathVariable Integer id) {
        serviceRepository.deleteById(id);
    }

    @PostMapping("/updateStatus")
    public void updateStatuses(@RequestBody UpdateServiceStatusDto request) {
        serviceRepository.updateStatuses(request.getServiceIds(), request.getStatus().name());
    }
}
