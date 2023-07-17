package com.Device.ConfigurationSoftware.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Device {

    @Id
    private Long pin_code;
    private String status;
    private int temperature;

    public Device() {
    }

    public Device(Long pin_code, String status, int temperature) {
        this.pin_code = pin_code;
        this.status = status;
        this.temperature = temperature;
    }

    public void setPin_code(Long pin_code) {
        this.pin_code = pin_code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Long getPin_code() {
        return pin_code;
    }

    public String getStatus() {
        return status;
    }

    public int getTemperature() {
        return temperature;
    }
}
