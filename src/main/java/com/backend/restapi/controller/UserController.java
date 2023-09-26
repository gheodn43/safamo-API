package com.backend.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.backend.restapi.dto.UserLoginedDto;
import com.backend.restapi.dto.UserProfileUpdateDto;
import com.backend.restapi.exception.UserNotFoundException;
import com.backend.restapi.service.UserService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/user-profile")
public class UserController {
    @Autowired
    private UserService userProfileService;
    
    @GetMapping("/{user_id}")
    public ResponseEntity<UserProfileUpdateDto> getUserProfile(@PathVariable("user_id") int userId) {
        try {
            UserProfileUpdateDto userProfile = userProfileService.getUserProfileByUserId(userId);

            if (userProfile != null) {
                return ResponseEntity.ok(userProfile);
            } else {
                return ResponseEntity.notFound().build(); // Trả về 404 Not Found
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Trả về lỗi 500 Internal Server Error
        }
    }



    @PostMapping("/update/{user_id}")
    public ResponseEntity<String> updateUserProfile(
        @PathVariable("user_id") int userId,
        @RequestBody UserProfileUpdateDto userProfileUpdateDto
    ) {
        try {
            userProfileService.updateUserProfile(userId, userProfileUpdateDto);
            return ResponseEntity.ok("Cập nhật thông tin hồ sơ thành công");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Người dùng không tồn tại: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật thông tin hồ sơ: " + e.getMessage());
        }
    }
}
