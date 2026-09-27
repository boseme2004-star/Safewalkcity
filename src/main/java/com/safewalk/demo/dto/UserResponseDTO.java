package com.safewalk.demo.dto;

import com.safewalk.demo.model.User;

public class UserResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private User.Role role;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.role = user.getRole();
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public User.Role getRole() {
        return role;
    }
}