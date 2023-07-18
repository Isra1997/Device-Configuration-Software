package com.Device.ConfigurationSoftware.controller;

import com.Device.ConfigurationSoftware.dto.DeviceDTO;
import com.Device.ConfigurationSoftware.entity.Device;
import com.Device.ConfigurationSoftware.exceptions.DeviceException;
import com.Device.ConfigurationSoftware.service.DeviceService;
import com.Device.ConfigurationSoftware.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @Autowired
    EncryptionService encryptionService;



    @GetMapping("/device")
    public ResponseEntity<DeviceDTO> getAllDevices(){
        return new ResponseEntity<>(new DeviceDTO(deviceService.getAllDevices()), HttpStatus.OK);
    }

    @PostMapping("/device")
    public ResponseEntity<DeviceDTO> addDevice(@RequestBody Device newDevice) {
        try {
            Device addedDevice = deviceService.addDevice(newDevice);
            return new ResponseEntity<>(new DeviceDTO(List.of(addedDevice)), HttpStatus.CREATED);
        } catch (DeviceException e) {
            return new ResponseEntity<>(new DeviceDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/configure/{id}")
    public ResponseEntity<DeviceDTO> configureDevice(@PathVariable Long id){
        try {
            return new ResponseEntity<>(new DeviceDTO(List.of(deviceService.configureDevice(id))), HttpStatus.OK);
        } catch (DeviceException e) {
            return new ResponseEntity<>(new DeviceDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/device/{id}")
    public ResponseEntity<DeviceDTO> deleteDevice(@PathVariable Long id){
        try {
            deviceService.deleteDevice(id);
        } catch (DeviceException e) {
            return new ResponseEntity<>(new DeviceDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DeviceDTO("Device deleted successfully."), HttpStatus.OK);
    }

    @PutMapping("/device")
    public ResponseEntity<DeviceDTO> editDevice(@RequestBody Device newDevice){
        try {
            return new ResponseEntity<>(new DeviceDTO(List.of(deviceService.updateDevice(newDevice))), HttpStatus.OK);
        } catch (DeviceException e) {
            return new ResponseEntity<>(new DeviceDTO(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


}
