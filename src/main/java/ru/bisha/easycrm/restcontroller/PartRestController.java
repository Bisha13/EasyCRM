package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.db.repository.PartRepository;

@RestController
@RequestMapping("/rest/parts")
@RequiredArgsConstructor
public class PartRestController {

    private final PartRepository partRepository;

    @DeleteMapping ("/{id}")
    public void deleteService(@PathVariable Integer id) {
        partRepository.deleteById(id);
    }
}
