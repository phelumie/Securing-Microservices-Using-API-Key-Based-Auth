package com.ajisegiri.customservice;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder

public class User {
    private String id;
    private String apikey;
    private String email;
    private boolean isActive;
    private Date joinDate;
}
