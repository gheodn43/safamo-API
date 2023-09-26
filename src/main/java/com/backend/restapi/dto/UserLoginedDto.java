package com.backend.restapi.dto;

import java.util.ArrayList;
import java.util.List;

public class UserLoginedDto {
	    private String username;
	    private List<String> roles = new ArrayList<>();

	    // Các phương thức getter và setter cho các thuộc tính

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public List<String> getRoles() {
	        return roles;
	    }

	    public void setRoles(List<String> roles) {
	        this.roles = roles;
	    }

	    public void addRole(String role) {
	        roles.add(role);
	    }
	}
