package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.UserEntity;
import ru.bisha.easycrm.db.repository.UserRepository;
import ru.bisha.easycrm.dto.GetWorkersResponse;
import ru.bisha.easycrm.dto.WorkerDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/workers")
@RequiredArgsConstructor
@ConditionalOnProperty(value = "ui", havingValue = "rest")
public class WorkersController {

    private final UserRepository userRepository;

    @GetMapping("/all")
    public GetWorkersResponse getAllUsers() {
        List<WorkerDto> workers = userRepository.findAll().stream()
                .map(u -> WorkerDto.builder()
                        .id(String.valueOf(u.getId()))
                        .name(u.getFullName())
                        .phone(u.getPhoneNumber())
                        .percent(u.getPercent())
                        .build())
                .collect(Collectors.toList());
        return GetWorkersResponse.builder().workers(workers).build();
    }

    @GetMapping("/{id}")
    public WorkerDto getUser(@PathVariable String id) {
        return userRepository.findById(Integer.valueOf(id))
                .map(u -> WorkerDto.builder()
                        .id(String.valueOf(u.getId()))
                        .name(u.getFullName())
                        .phone(u.getPhoneNumber())
                        .percent(u.getPercent())
                        .build())
                .orElseThrow();
    }

    @PostMapping
    public void updateWorker(@RequestBody WorkerDto worker) {
        UserEntity userEntity = UserEntity.builder()
                .id(Integer.parseInt(worker.getId()))
                .fullName(worker.getName())
                .phoneNumber(worker.getPhone())
                .percent(worker.getPercent())
                .build();
        userRepository.save(userEntity);
    }
}
