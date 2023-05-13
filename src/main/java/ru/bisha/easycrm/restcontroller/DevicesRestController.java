package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.Device;
import ru.bisha.easycrm.db.repository.DeviceRepository;
import ru.bisha.easycrm.dto.DeviceDto;

import java.util.List;
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
                        .name(d.getDeviceName())
                        .description(d.getDescription())
                        .createdAt(d.getTimestamp().toLocalDateTime())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DeviceDto getById(@PathVariable Integer id) {
        Device device = deviceRepository.getOne(id);
        return DeviceDto.builder()
                .id(String.valueOf(device.getDeviceId()))
                .name(device.getDeviceName())
                .description(device.getDescription())
                .clientId(String.valueOf(device.getOwnerId()))
                .createdAt(device.getTimestamp().toLocalDateTime())
                .build();
    }


    @PutMapping
    public void updateDevice(@RequestBody DeviceDto deviceDto) {
        Device device = StringUtils.hasLength(deviceDto.getId()) ?
                deviceRepository.findById(Integer.valueOf(deviceDto.getId())).orElseThrow() :
                new Device();
        device.setDeviceName(deviceDto.getName());
        device.setDescription(deviceDto.getDescription());
        device.setOwnerId(Integer.parseInt(deviceDto.getClientId()));
        deviceRepository.saveAndFlush(device);
    }
}
