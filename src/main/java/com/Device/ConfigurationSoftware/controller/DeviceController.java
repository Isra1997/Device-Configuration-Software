package com.Device.ConfigurationSoftware.controller;

import com.Device.ConfigurationSoftware.entity.Device;
import com.Device.ConfigurationSoftware.exceptions.DeviceException;
import com.Device.ConfigurationSoftware.service.DeviceService;
import com.Device.ConfigurationSoftware.service.EncryptionService;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @Autowired
    EncryptionService encryptionService;



    @GetMapping("/device")
    public ResponseEntity<List<Device>> getAllDevices(){
        return new ResponseEntity<>(deviceService.getAllDevices(), HttpStatus.OK);
    }

    @PostMapping("/device")
    public ResponseEntity addDevice(@RequestBody Device newDevice) {
        try {
            Device addedDevice = deviceService.addDevice(newDevice);
            return new ResponseEntity<>(addedDevice, HttpStatus.OK);
        } catch (DeviceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/configure/{pin_code}")
    public ResponseEntity configureDevice(@PathVariable Long id){
        try {
            return new ResponseEntity<>(deviceService.configureDevice(id), HttpStatus.OK);
        } catch (DeviceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/device/{pin_code}")
    public ResponseEntity<String> deleteDevice(@PathVariable Long pin_code){
        try {
            deviceService.deleteDevice(pin_code);
        } catch (DeviceException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("Device deleted successfully.", HttpStatus.OK);
    }

    @PutMapping("/device")
    public ResponseEntity<String> editDevice(@RequestBody Device newDevice){
        try {
            return new ResponseEntity(deviceService.updateDevice(newDevice), HttpStatus.OK);
        } catch (DeviceException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
