package com.example.foodcourt;

import java.util.ArrayList;
import java.util.Optional;

import com.example.foodcourt.models.Auth;
import com.example.foodcourt.models.Member;
import com.example.foodcourt.models.Owner;

public class Storage {
    private static Storage storage;
    private final ArrayList<Member> members = new ArrayList<>();
    private final ArrayList<Owner> owners = new ArrayList<>();
    private Optional<Owner> loggedInOwner;
    private Optional<Member> loggedInMember;


    private final Auth root = new Auth("root", "1234");

    public static Storage getInstance() {
        if(storage == null) {
            storage = new Storage();
        }

        return storage;
    }

    public ArrayList<Owner> getOwners() {
        return owners;
    }

    public boolean isRoot(Auth auth) {
        return this.root.equals(auth);
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public Optional<Owner> getLoggedInOwner() {
        return loggedInOwner;
    }

    public Optional<Member> getLoggedInMember() {
        return loggedInMember;
    }

    public void setLoggedInMember(Optional<Member> loggedInMember) {
        if(loggedInMember.isPresent() && loggedInOwner.isPresent()) {
            loggedInOwner = Optional.empty(); // owner logOut
            this.loggedInMember = loggedInMember;
        }
        else this.loggedInMember = loggedInMember;
    }

    public void setLoggedInOwner(Optional<Owner> loggedInOwner) {
        if(loggedInOwner.isPresent() && loggedInMember.isPresent()) {
            loggedInMember = Optional.empty(); // member logOut
            this.loggedInOwner = loggedInOwner;
        }
        else this.loggedInOwner = loggedInOwner;
    }
}
