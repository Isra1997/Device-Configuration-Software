package com.Device.ConfigurationSoftware.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String pin_code;
    private String status;
    private int temperature;

    private String encryptKey;

    public Device() {
    }

    public Device(String pin_code, String status, int temperature) {
        this.pin_code = pin_code;
        this.status = status;
        this.temperature = temperature;
    }

    @JsonIgnore
    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPin_code() {
        return pin_code;
    }

    public String getStatus() {
        return status;
    }

    public int getTemperature() {
        return temperature;
    }
}
