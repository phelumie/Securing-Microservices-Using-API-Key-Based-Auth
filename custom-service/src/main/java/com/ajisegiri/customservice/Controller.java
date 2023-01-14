package com.ajisegiri.customservice;


import com.ajisegiri.Common.model.hashes.Auth;
import com.ajisegiri.Common.repo.AuthRepository;
import com.ajisegiri.Common.service.BloomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class Controller {
    private final BloomService bloomService;
    private final AuthRepository authRepository;
    @Value("${spring.application.name}")
    private String serviceName;
    private HashMap<String,User> users=new HashMap<>();

    @GetMapping("welcome")
    public String welcome(){
        return "welcome";

    }
    @PostMapping("api-key")
    public ResponseEntity<String> requestApiKey(@RequestBody String email){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

        String apiKey= generateApiKey(40);

        while (bloomService.checkIfExistsApiKey(apiKey))
            apiKey= generateApiKey(40);

        var userBody=User.builder()
                .id(UUID.randomUUID().toString())
                .joinDate(Date.from(new Date().toInstant()))
                .apikey(apiKey)
                .email(email)
                .isActive(true).build();

        var authBody=
                Auth.builder()
                        .key(userBody.getApikey()).isAccountActivated(userBody.isActive())
                        .services(Set.of(serviceName))
                        .build();
        Optional<Auth> auth=authRepository.findById(apiKey);

        auth.ifPresentOrElse(data->{
            data.addServices(serviceName);
            data.setAccountActivated(userBody.isActive());
            authRepository.save(data);
        }, ()->authRepository.save(authBody));

        users.put(apiKey,userBody);
        bloomService.add(apiKey);
        return new ResponseEntity<>("This is your api apiKey "+ apiKey,HttpStatus.CREATED);
    }

    public String generateApiKey(int length) {
        Random random = new Random();
        char[] characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = characters[random.nextInt(characters.length)];
        }
        return new String(result);
    }
    private String hashApikey(String apikey){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode(apikey);
        return result;
    }


}
