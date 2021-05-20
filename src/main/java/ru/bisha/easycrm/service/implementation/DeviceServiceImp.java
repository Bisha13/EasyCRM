
package ru.bisha.easycrm.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bisha.easycrm.db.entity.Device;
import ru.bisha.easycrm.db.repository.DeviceRepository;
import ru.bisha.easycrm.service.DeviceService;

@Service
public class DeviceServiceImp implements DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    @Override
    public Device getDevice(int id) {
        return deviceRepository.findById(id).orElseThrow();
    }
}
