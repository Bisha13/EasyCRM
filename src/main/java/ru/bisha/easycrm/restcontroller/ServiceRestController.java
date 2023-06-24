package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.repository.ServiceRepository;
import ru.bisha.easycrm.dto.UpdateServiceStatusDto;

@RestController
@RequestMapping("/rest/services")
@RequiredArgsConstructor
@ConditionalOnProperty(value = "ui", havingValue = "rest")
public class ServiceRestController {

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
