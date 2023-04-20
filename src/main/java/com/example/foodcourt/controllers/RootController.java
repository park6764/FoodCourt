package com.example.foodcourt.controllers;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodcourt.Storage;
import com.example.foodcourt.models.Auth;
import com.example.foodcourt.models.Member;
import com.example.foodcourt.models.Owner;
import com.example.foodcourt.models.Shop;

@RestController
@RequestMapping("/root/")
public class RootController {

    // 관리자(root) - 음식점 등록(수정, 삭제) - 영업자 정보 - 회원 정보

    @GetMapping("/") 
    public String root(
        @RequestParam(name = "id") String id,
        @RequestParam(name = "pw") String pw
    ) {
        if(Storage.getInstance().isRoot(new Auth(id, pw))) return "[ 마스터 ]님 환영합니다.";
        else return "아이디 또는 비밀호가 다름니다.";
    }

    @GetMapping("/shopList")
    public ArrayList<Shop> shopList() {
        return new ArrayList<>(Storage.getInstance().getOwners().stream().map(o -> o.getShop()).toList());
    }

    @GetMapping("/userInfo")
    public ArrayList<Member> usersInfo() {
        return Storage.getInstance().getMembers();
    }

    @GetMapping("/OwnerInfo")
    public ArrayList<Owner> OwnersInfo() {
        return Storage.getInstance().getOwners();
    }
}
