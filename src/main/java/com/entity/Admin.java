package com.entity;

import lombok.Data;

import java.util.List;

@Data
public class Admin {
    private int adminId;
    private String adminName;
    private String adminPassword;
    private String gender;
    private String adminAccountName;
    private List<String> roles;
}
