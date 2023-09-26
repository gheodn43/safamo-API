package com.backend.restapi.controller;


import com.backend.restapi.exception.DuplicateRoleException;
import com.backend.restapi.exception.PropertyRoleNotFoundException;
import com.backend.restapi.exception.UnauthorizedException;
import com.backend.restapi.models.PropertyRole;
import com.backend.restapi.repository.PropertyRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propertyRoles")
public class PropertyRoleController {

    @Autowired
    private PropertyRoleRepository propertyRoleRepository;

    @PostMapping("/create")
    public PropertyRole createPropertyRole(@RequestBody PropertyRole propertyRole,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        // Kiểm tra xem người dùng có vai trò SUPERADMIN không
        if (userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("SUPERADMIN"))) {
            if (propertyRoleRepository.existsByTypeName(propertyRole.getTypeName())) {
                throw new DuplicateRoleException("Property Role with the same name already exists");
            } else {
            	return propertyRoleRepository.save(propertyRole);
            } 
        } else {
            throw new UnauthorizedException("Access denied");
        }
    }
    @PutMapping("/update/{id}")
    public PropertyRole updatePropertyRole(@PathVariable Integer id,
                                           @RequestBody PropertyRole updatedPropertyRole,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        // Kiểm tra xem người dùng có vai trò SUPERADMIN không
        if (userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("SUPERADMIN"))) {
            PropertyRole existingPropertyRole = propertyRoleRepository.findById(id)
                    .orElseThrow(() -> new PropertyRoleNotFoundException("Role not found"));
            existingPropertyRole.setTypeName(updatedPropertyRole.getTypeName());
            // Cập nhật các trường khác của PropertyRole nếu cần
            return propertyRoleRepository.save(existingPropertyRole);
        } else {
            throw new UnauthorizedException("Access denied");
        }
    }
    @GetMapping("/view")
    public List<PropertyRole> viewPropertyRoles() {
        return propertyRoleRepository.findAll();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePropertyRole(@PathVariable Integer id,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("SUPERADMIN"))) {
            PropertyRole existingPropertyRole = propertyRoleRepository.findById(id)
                    .orElseThrow(() -> new PropertyRoleNotFoundException("Role not found"));
            propertyRoleRepository.delete(existingPropertyRole);
            return ResponseEntity.ok().build();
        } else {
            throw new UnauthorizedException("Access denied");
        }
    }
}
