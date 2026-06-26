package com.example.__learn.dto;

public class StudentResponse {
    private String id;
    private String name;
    private String city;
    private String email;
    private Role role;


    public StudentResponse() {
    }

    public StudentResponse(String id, String name, String city, String email, Role role) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
