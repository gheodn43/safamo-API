package com.backend.restapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.backend.restapi.dto.UserProfileUpdateDto;
import com.backend.restapi.exception.UserNotFoundException;
import com.backend.restapi.models.UserEntity;
import com.backend.restapi.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void updateUserProfile(@RequestParam int user_id, @RequestBody UserProfileUpdateDto userProfileUpdateDto) {
        String fullname = userProfileUpdateDto.getFullname();
        String birthdate = userProfileUpdateDto.getBirthdate();
        String email = userProfileUpdateDto.getEmail();
        String phone_number = userProfileUpdateDto.getPhone_number();
        String cccd = userProfileUpdateDto.getCccd();
        String address = userProfileUpdateDto.getAddress();
        String avatarUrl = userProfileUpdateDto.getAvatarUrl();

        Optional<UserEntity> userOptional = userRepository.findById(user_id);

        // Cập nhật thông tin hồ sơ
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setFullname(fullname);
            user.setBirthdate(birthdate);
            user.setEmail(email);
            user.setPhone_number(phone_number);
            user.setCccd(cccd);
            user.setAddress(address);
            user.setAvatarUrl(avatarUrl);
            userRepository.save(user);
        }
    }

    
    public int getUserIdByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getUser_id(); 
        } else {
            throw new UserNotFoundException("Không tìm thấy người dùng với username: " + username);
        }
    }
    
    public UserProfileUpdateDto getUserProfileByUserId(int userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            UserProfileUpdateDto userProfileUpdateDto = new UserProfileUpdateDto();
            userProfileUpdateDto.setFullname(userEntity.getFullname());
            userProfileUpdateDto.setBirthdate(userEntity.getBirthdate());
            userProfileUpdateDto.setEmail(userEntity.getEmail());
            userProfileUpdateDto.setPhone_number(userEntity.getPhone_number());
            userProfileUpdateDto.setCccd(userEntity.getCccd());
            userProfileUpdateDto.setAddress(userEntity.getAddress());
            userProfileUpdateDto.setAvatarUrl(userEntity.getAvatarUrl());
            return userProfileUpdateDto;
        } else {
            return null;
        }
    }
}
