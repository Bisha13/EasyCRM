package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.db.repository.ServiceRepository;

@RestController
@RequestMapping("/rest/services")
@RequiredArgsConstructor
public class ServiceRestController {

    private final ServiceRepository serviceRepository;

    @DeleteMapping ("/{id}")
    public void deleteService(@PathVariable Integer id) {
        serviceRepository.deleteById(id);
    }
}
