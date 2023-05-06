package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bisha.easycrm.db.repository.DeviceRepository;
import ru.bisha.easycrm.dto.DeviceDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/devices")
@RequiredArgsConstructor
public class DevicesRestController {

    private final DeviceRepository deviceRepository;

    @GetMapping("/by_client")
    public List<DeviceDto> getByClientId(@RequestParam String client) {
        return deviceRepository.getDevicesByOwnerId(Integer.valueOf(client)).stream()
                .map(d -> DeviceDto.builder()
                        .id(String.valueOf(d.getDeviceId()))
                        .description(Optional.ofNullable(d.getDeviceName()).map(n -> n + " ").orElse("") + d.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
