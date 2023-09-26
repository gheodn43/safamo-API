package com.backend.restapi.dto;

import java.util.List;

import lombok.Data;

@Data
public class AuthResponseDTO {
	private int user_id;
    private String accessToken;
    private String username;
    private List<String> roles;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(int user_id, String accessToken, String username, List<String> roles) {
    	this.user_id = user_id;
        this.accessToken = accessToken;
        this.username = username;
        this.roles = roles;
    }

	public AuthResponseDTO() {
		super();
	}
	

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

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

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
    
}
