package com.beditsch.project.dto;

import javax.validation.constraints.NotEmpty;

public class UserSignUpRequest {
    @NotEmpty(message = "Please provide a valid username.")
    private String username;
    private String email;
    @NotEmpty(message = "Please provide a valid password.")
    private String password;




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
