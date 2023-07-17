package com.Device.ConfigurationSoftware.service;

import com.Device.ConfigurationSoftware.entity.Device;
import com.Device.ConfigurationSoftware.exceptions.DeviceException;
import com.Device.ConfigurationSoftware.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

@Service
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    EncryptionService encryptionService;


    public List<Device> getAllDevices(){
        List<Device> allDevices = (List<Device>) deviceRepository.findAll();
        // sorting the devices according to the device id
        allDevices = allDevices.stream()
                .peek( device -> device.setPin_code(encryptionService.decryptValue(device.getPin_code(),device.getEncryptKey())))
                .sorted(Comparator.comparingLong(device -> Long.parseLong(device.getPin_code())))
                .toList();
        return allDevices;
    }

    public Optional<Device> getDeviceById(Long id){
        return  deviceRepository.findById(id);
    }

    public Optional<Device> getDeviceByPinCode(String pin_code)
    {
        List<Device> allDevices = (List<Device>) deviceRepository.findAll();
        // sorting the devices according to the device id
        return allDevices.stream()
                .filter(device -> encryptionService.decryptValue(device.getPin_code(),device.getEncryptKey()).equals(pin_code)).findFirst();
    }

    public Device addDevice(Device newDevice) throws DeviceException {
        // check if the string contains 7 digits
        try {
            Long.parseLong(newDevice.getPin_code());
        }catch (NumberFormatException e){
            throw new DeviceException("Please enter a 7 digit pin code");
        }
        // check if the pin is 7 digits long
        if (newDevice.getPin_code().length() != 7){
           throw new DeviceException("A device pin code has to be 7 digits long.");
        } else if(getDeviceByPinCode(newDevice.getPin_code()).isPresent()){
            throw new DeviceException("A device with the given pin code already exists.");
        }else {
            // generate a random key
            SecureRandom secureRandom = new SecureRandom();
            byte [] salt = new byte[16];
            secureRandom.nextBytes(salt);
            String key = Base64.getEncoder().encodeToString(salt);
            // encrypt the pin_code to be stored in the database
            String hashedPin_code= encryptionService.encryptValue(newDevice.getPin_code(), key);
            newDevice.setPin_code(hashedPin_code);
            newDevice.setEncryptKey(key);
            //add the device to the database
            Device addedDevice = deviceRepository.save(newDevice);
            addedDevice.setPin_code(encryptionService.decryptValue(addedDevice.getPin_code(),addedDevice.getEncryptKey()));
            return addedDevice;
        }
    }

    public void deleteDevice(Long id) throws DeviceException {
        if(getDeviceById(id).isPresent()){
            deviceRepository.deleteById(id);
        }else {
            throw new DeviceException("Device not found.");
        }
    }

    public Device updateDevice(Device newDevice) throws DeviceException {
        if(getDeviceById(newDevice.getId()).isPresent()){
            return deviceRepository.save(newDevice);
        }else {
            throw new DeviceException("Device not found.");
        }
    }

    public Device configureDevice(Long id) throws DeviceException {
        Optional<Device> fetchedDevice = getDeviceById(id);
        if(fetchedDevice.isPresent()) {
            Device configuredDevice = fetchedDevice.get();
            // set the state to active
            configuredDevice.setStatus("ACTIVE");
            // generate a random number and set the temperature
            Random random = new Random();
            int randomNumber = random.nextInt(11);
            configuredDevice.setTemperature(randomNumber);
            return deviceRepository.save(configuredDevice);
        }else {
            throw new DeviceException("Device not found.");
        }
    }
}
