package com.sertifikasi.learn.controller.managements;

import com.sertifikasi.learn.dto.request.LoginRequest;
import com.sertifikasi.learn.dto.request.RegisterRequest;
import com.sertifikasi.learn.dto.response.ApiSignInResponseBuilder;
import com.sertifikasi.learn.dto.response.ApiResponse;
import com.sertifikasi.learn.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-management")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/users/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/users/sign-in")
    public ResponseEntity<Object> signIn(@Valid @RequestBody LoginRequest loginRequest) {
        ApiSignInResponseBuilder result = userService.signIn(loginRequest);
        return ResponseEntity.status(result.getStatus()).body(result);
    }

}
