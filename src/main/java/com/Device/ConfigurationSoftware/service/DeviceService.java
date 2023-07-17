package com.Device.ConfigurationSoftware.service;

import com.Device.ConfigurationSoftware.entity.Device;
import com.Device.ConfigurationSoftware.exceptions.DeviceException;
import com.Device.ConfigurationSoftware.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepository;

    public List<Device> getAllDevices(){
        return (List<Device>) deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Long pin_code){
        return  deviceRepository.findById(pin_code);
    }

    public Device addDevice(Device newDevice) throws DeviceException {
        // check if a device with the same pic code exists
        if(getDeviceById(newDevice.getPin_code()).isPresent()){
            throw new DeviceException("A device with this pin code already exists.");
            // check if the pin is 7 digits long
        }else if (Long.toString(newDevice.getPin_code()).length() != 7){
           throw new DeviceException("A device pin code has to be 7 digits long.");
        } else {
            return deviceRepository.save(newDevice);
        }
    }

    public void deleteDevice(Long pin_code) throws DeviceException {
        if(getDeviceById(pin_code).isPresent()){
            deviceRepository.deleteById(pin_code);
        }else {
            throw new DeviceException("Device not found.");
        }
    }

    public Device updateDevice(Device newDevice) throws DeviceException {
        if(getDeviceById(newDevice.getPin_code()).isPresent()){
            return deviceRepository.save(newDevice);
        }else {
            throw new DeviceException("Device not found.");
        }
    }

    public Device configureDevice(Long pin_code) throws DeviceException {
        Optional<Device> fetchedDevice = getDeviceById(pin_code);
        if(fetchedDevice.isPresent()) {
            Device configuredDevice = fetchedDevice.get();
            configuredDevice.setStatus("ACTIVE");
            Random random = new Random();
            int randomNumber = random.nextInt(11);
            configuredDevice.setTemperature(randomNumber);
            return deviceRepository.save(configuredDevice);
        }else {
            throw new DeviceException("Device not found.");
        }
    }
}
