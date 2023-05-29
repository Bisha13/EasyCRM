
package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.DeviceEntity;
import ru.bisha.easycrm.db.repository.DeviceRepository;
import ru.bisha.easycrm.service.DeviceService;

import java.util.List;

@Service
public class DeviceServiceImp implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public DeviceEntity getDevice(int id) {
        return deviceRepository.findById(id).orElseThrow();
    }

    @Override
    public List<DeviceEntity> getDevicesByUserId(int id) {
        return deviceRepository.getDevicesByOwnerId(id);
    }

    @Override
    public List<DeviceEntity> getAll() {
        return deviceRepository.findAll();
    }

    @Override
    public void saveDevice(DeviceEntity device) {
        deviceRepository.save(device);
    }

    @Override
    public Page<DeviceEntity> getPageOfDevices(PageRequest request) {
        return deviceRepository.findAll(request);
    }

    @Override
    public Page<DeviceEntity> getDevicesBySearch(String search, PageRequest request) {
        return deviceRepository.getDevicesBySearch("%" + search + "%", request);
    }
}
