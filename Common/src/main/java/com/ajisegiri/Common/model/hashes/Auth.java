package com.ajisegiri.Common.model.hashes;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash
@Builder
public class Auth {
    @Id
    private String key;
    private String UserId;
    private boolean isAccountActivated;
    private Set<String> services;
    @CreatedDate
    private Date createdDate;

    public void  addServices(String service){
        if (this.services==null)
            this.services=new HashSet<>();
        this.services.add(service);
    }
}
