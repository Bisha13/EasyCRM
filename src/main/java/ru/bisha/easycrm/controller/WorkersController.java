package ru.bisha.easycrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.WorkerEntity;
import ru.bisha.easycrm.db.repository.WorkerRepository;
import ru.bisha.easycrm.dto.GetWorkersResponse;
import ru.bisha.easycrm.dto.WorkerDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/workers")
@RequiredArgsConstructor
public class WorkersController {

    private final WorkerRepository workerRepository;

    @GetMapping("/all")
    public GetWorkersResponse getAllUsers() {
        List<WorkerDto> workers = workerRepository.findAll().stream()
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
        return workerRepository.findById(Integer.valueOf(id))
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
        WorkerEntity workerEntity = WorkerEntity.builder()
                .id(Integer.parseInt(worker.getId()))
                .fullName(worker.getName())
                .phoneNumber(worker.getPhone())
                .percent(worker.getPercent())
                .build();
        workerRepository.save(workerEntity);
    }
}
