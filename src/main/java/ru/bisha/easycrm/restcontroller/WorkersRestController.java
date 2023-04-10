package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.db.repository.UserRepository;
import ru.bisha.easycrm.dto.GetWorkersResponse;
import ru.bisha.easycrm.dto.WorkerDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/workers")
@RequiredArgsConstructor
public class WorkersRestController {

    private final UserRepository userRepository;

    @GetMapping("/all")
    public GetWorkersResponse getAllUsers() {
        List<WorkerDto> workers = userRepository.findAll().stream()
                .map(u -> WorkerDto.builder()
                        .id(String.valueOf(u.getId()))
                        .name(u.getFullName())
                        .build())
                .collect(Collectors.toList());
        return GetWorkersResponse.builder().workers(workers).build();
    }
}
