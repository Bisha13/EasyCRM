package ru.bisha.easycrm.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.bisha.easycrm.db.entity.DeviceEntity;
import ru.bisha.easycrm.db.repository.DeviceRepository;
import ru.bisha.easycrm.dto.DeviceDto;
import ru.bisha.easycrm.dto.GetDevicesResponse;
import ru.bisha.easycrm.service.DeviceService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/devices")
@RequiredArgsConstructor
@ConditionalOnProperty(value = "ui", havingValue = "rest")
public class DevicesRestController {

    private static final String DEFAULT_PAGE_SIZE = "100";

    private final DeviceRepository deviceRepository;

    private final DeviceService deviceService;

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
        DeviceEntity device = deviceRepository.getOne(id);
        return DeviceDto.builder()
                .id(String.valueOf(device.getDeviceId()))
                .name(device.getDeviceName())
                .description(device.getDescription())
                .clientId(String.valueOf(device.getOwnerId()))
                .createdAt(device.getTimestamp().toLocalDateTime())
                .build();
    }

    @GetMapping("/all")
    public GetDevicesResponse getAllDevices(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) {

        Page<DeviceEntity> devicePage = deviceService.getPageOfDevices(
                PageRequest.of(page - 1, size,
                        Sort.by("deviceId").descending()));

        int totalPages = devicePage.getTotalPages();

        List<DeviceDto> devices = devicePage.stream()
                .map(d -> DeviceDto.builder()
                        .id(String.valueOf(d.getDeviceId()))
                        .name(d.getDeviceName())
                        .description(d.getDescription())
                        .createdAt(d.getTimestamp().toLocalDateTime())
                        .build())
                .collect(Collectors.toList());

        return GetDevicesResponse.builder()
                .devices(devices)
                .pageCount(totalPages)
                .build();
    }


    @PutMapping
    public void updateDevice(@RequestBody DeviceDto deviceDto) {
        DeviceEntity device = StringUtils.hasLength(deviceDto.getId()) ?
                deviceRepository.findById(Integer.valueOf(deviceDto.getId())).orElseThrow() :
                new DeviceEntity();
        device.setDeviceName(deviceDto.getName());
        device.setDescription(deviceDto.getDescription());
        device.setOwnerId(Integer.parseInt(deviceDto.getClientId()));
        deviceRepository.saveAndFlush(device);
    }
}
