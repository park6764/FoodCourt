package com.example.foodcourt;

import java.time.LocalDate;

public class Owner {
    private String name;
    private LocalDate birth;
    private Auth auth;
    
    public Owner(String name, LocalDate birth, Auth auth) {
        this.name = name;
        this.birth = birth;
        this.auth = auth;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public Auth getAuth() {
        return auth;
    }

    
}
