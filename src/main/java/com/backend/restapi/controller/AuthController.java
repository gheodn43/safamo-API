package com.backend.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.restapi.dto.AuthResponseDTO;
import com.backend.restapi.dto.LoginDto;
import com.backend.restapi.dto.RegisterDto;
import com.backend.restapi.exception.UserNotFoundException;
import com.backend.restapi.models.Role;
import com.backend.restapi.models.UserEntity;
import com.backend.restapi.repository.RoleRepository;
import com.backend.restapi.repository.UserRepository;
import com.backend.restapi.security.JWTGenerator;
import com.backend.restapi.service.OtpService;
import com.backend.restapi.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;

    @Autowired
    private UserService userService;
    @Autowired
    private OtpService otpService ;
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("login")
    public AuthResponseDTO login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
        		new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        int userId = getUserIdFromDatabaseOrStorage(loginDto.getUsername());
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
                SecurityContextHolder.getContext().setAuthentication(authentication);
                authResponseDTO.setUser_id(userId);
                authResponseDTO.setAccessToken(jwtGenerator.generateToken(authentication));
                authResponseDTO.setUsername(loginDto.getUsername());
                
                List<String> roles = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                authResponseDTO.setRoles(roles);
                return authResponseDTO;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        if (!otpService.verifyOTP(registerDto.getEmail(), registerDto.getOtp())) {
            return new ResponseEntity<>("Invalid OTP!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
        user.setEmail(registerDto.getEmail());
        Role roles = roleRepository.findByName("CUSTOMER").orElse(null);
        if (roles != null) {
            user.setRoles(Collections.singletonList(roles));
        }

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
    }
    private int getUserIdFromDatabaseOrStorage(String username) {
        try {
            return userService.getUserIdByUsername(username);
        } catch (UserNotFoundException e) {
            return 0; 
        }
    }
}