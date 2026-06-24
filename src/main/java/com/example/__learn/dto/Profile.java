package com.example.__learn.dto;

public class Profile {
    private String email;
    private String uname;
    private Role role;

    public Profile() {
    }

    public Profile(String email, String uname, Role role) {
        this.email = email;
        this.uname = uname;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
