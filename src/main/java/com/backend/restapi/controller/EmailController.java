package com.backend.restapi.controller;

import com.backend.restapi.dto.RegisterDto;
import com.backend.restapi.repository.UserRepository;
import com.backend.restapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class EmailController {

    private final EmailService emailService;
    private UserRepository userRepository;

    @Autowired
    public EmailController(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @PostMapping("sendOtpEmailRegister")
    public ResponseEntity<String> sendOtpEmailRegister(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email is taken!", HttpStatus.BAD_REQUEST);
        }
        try {
            emailService.sendOtpEmailRegister(registerDto.getEmail(), registerDto.getUsername());
            return ResponseEntity.ok("Email OTP đã được gửi thành công.");
        } catch (Exception e) {
            return new ResponseEntity<>("Gửi email OTP thất bại: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
