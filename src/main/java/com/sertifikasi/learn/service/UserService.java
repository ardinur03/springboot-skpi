package com.sertifikasi.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sertifikasi.learn.dto.request.LoginRequest;
import com.sertifikasi.learn.dto.request.RegisterRequest;
import com.sertifikasi.learn.dto.response.ApiResponse;
import com.sertifikasi.learn.dto.response.ApiSignInResponseBuilder;
import com.sertifikasi.learn.dto.response.JwtResponse;
import com.sertifikasi.learn.model.User;
import com.sertifikasi.learn.repository.UserRepository;
import com.sertifikasi.learn.security.auth.JwtUtil;
import com.sertifikasi.learn.service.auth.UserDetailsImplement;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lib.i18n.utility.MessageUtil;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private Validator validator;

        @Autowired
        private MessageUtil messageUtil;

        @Autowired
        AuthenticationManager authenticationManager;

        @Autowired
        JwtUtil jwtUtil;

        final HttpStatus statusOK = HttpStatus.OK;

        @Transactional
        public ApiResponse register(RegisterRequest request) {
                Set<ConstraintViolation<RegisterRequest>> constraintViolations = validator.validate(request);
                if (!constraintViolations.isEmpty()) {
                        ConstraintViolation<RegisterRequest> firstViolation = constraintViolations.iterator().next();
                        return new ApiResponse(firstViolation.getMessage(), HttpStatus.BAD_REQUEST.value(), "ERROR");
                }

                if (userRepository.existsByUsername(request.getUsername())) {
                        return new ApiResponse(messageUtil.get("message.error.already-exist.user"),
                                        HttpStatus.BAD_REQUEST.value(),
                                        "ERROR");
                }

                if (!request.getPassword().equals(request.getRetypePassword())) {
                        return new ApiResponse(messageUtil.get("message.error.password-not-match.user"),
                                        HttpStatus.BAD_REQUEST.value(), "ERROR");
                }

                if (request.getPassword().length() < 6) {
                        return new ApiResponse(messageUtil.get("message.error.password-validation.user"),
                                        HttpStatus.BAD_REQUEST.value(), "ERROR");
                }

                User user = User.builder()
                                .username(request.getUsername())
                                .fullname(request.getFullname())
                                .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
                                .role("User")
                                .isDeleted(false)
                                .build();

                userRepository.save(user);
                return new ApiResponse(messageUtil.get("message.success.registered.user", request.getUsername()),
                                HttpStatus.OK.value(), "OK");
        }

        public ApiSignInResponseBuilder signIn(LoginRequest loginRequest) {
                try {
                        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                        loginRequest.getPassword()));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        String jwt = jwtUtil.generateJwtToken(authentication);

                        UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
                        Optional<String> roles = userDetails.getAuthorities().stream()
                                        .map(item -> item.getAuthority())
                                        .findFirst();

                        return ApiSignInResponseBuilder.builder()
                                        .data(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                                                        roles.get()))
                                        .message(messageUtil.get("message.success.auth.user"))
                                        .statusCode(statusOK.value())
                                        .status(statusOK)
                                        .build();

                } catch (AuthenticationException e) {
                        return ApiSignInResponseBuilder.builder()
                                        .message(messageUtil.get("message.error.auth.user"))
                                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                                        .status(HttpStatus.UNAUTHORIZED)
                                        .build();
                } catch (Exception e) {
                        return ApiSignInResponseBuilder.builder()
                                        .message(messageUtil.get("message.error.internal.server.error"))
                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .build();
                }
        }
}
