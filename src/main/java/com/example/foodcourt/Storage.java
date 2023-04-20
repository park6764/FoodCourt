package com.example.foodcourt;

import java.util.ArrayList;

public class Storage {
    private static Storage storage;
    private final ArrayList<Member> members = new ArrayList<>();
    private final ArrayList<Owner> owners = new ArrayList<>();

    private final Auth root = new Auth("root", "1234");

    public static Storage getInstance() {
        if(storage == null) {
            storage = new Storage();
        }

        return storage;
    }

    public ArrayList<Member> getUsers() {
        return members;
    }

    public ArrayList<Owner> getOwners() {
        return owners;
    }

    public boolean isRoot(Auth auth) {
        return this.root.equals(auth);
    }
}
