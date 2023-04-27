package com.example.foodcourt.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodcourt.Storage;
import com.example.foodcourt.models.Auth;
import com.example.foodcourt.models.Member;

@RestController
public class MemberController {

    @GetMapping("/")
    public String main() {
        return "<푸드코드_test>";
    }

    @GetMapping("/signUp")
    public String signUp(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth,
            @RequestParam(name = "addr") String addr,
            @RequestParam(name = "id") String id,
            @RequestParam(name = "pw") String pw
    ) {
        var info = Storage.getInstance().getMembers().stream().filter(m -> m.equals(new Auth(id, pw))).findFirst();
        if (info.isEmpty()) {
            Storage.getInstance().getMembers().add(new Member(name, birth, addr, new Auth(id, pw), 0, new ArrayList()));
            return "[ " + name + " ]님 환영합니다.";
        } else {
            return "이미 있는 아이디 입니다.";
        }
    }

    @GetMapping("/login")
    public String login(
        @RequestParam(name = "id") String id,
        @RequestParam(name = "pw") String pw
    ) {
        var info = Storage.getInstance().getMembers().stream().filter(m -> m.getAuth().equals(new Auth(id, pw))).findFirst();

        if(info.isPresent()) return "[ " + info.get().getName() + " ]님 환영합니다.";
        else return "아이디 또는 비밀호가 다름니다.";
    }

    @GetMapping("/findMemberAuth")
    public String findMemberAuth(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth
    ) {
        
        var info = Storage.getInstance().getMembers().stream().filter(m -> m.getName().equals(name) && m.getBirth().equals(birth)).findFirst();
        

        if(info.isPresent()) {
            var i = info.get().getAuth().getId();
            var p = info.get().getAuth().getPw();
            return "[ " + name + " ]님 의 id와 pw 는 [ " + i + " : " + p + " ] 입니다.";
        } else return "[ " + name + "]님은 회원이 아닙니다."; 
    }

    @GetMapping("/login/withdrawal")
    public String memberWithdrawal() {
        
        var info = Storage.getInstance().getLoggedInMember();
        
        if (info.isPresent()) {
            var n = info.get().getName();
            Storage.getInstance().getMembers().remove(info.get());
            return "[ " + n + " ]님은 탈퇴하셨습니다.";
        } else {
            return "먼저 로그인해주세요.";
        }
    }
}


/*


회원(3) - 회원가입 - 로그인(id 찾기, pw찾기(비번변경), 회원탈퇴) 

 */
