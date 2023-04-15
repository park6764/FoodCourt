package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerController {

    final ArrayList<Owner> owners = new ArrayList<>();

    @GetMapping("/ownerSignUp")
    public String ownerSignUp(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth,
            @RequestParam(name = "id") String id,
            @RequestParam(name = "pw") String pw
    ) {
        var ow = MemberController.findEl(m -> m.getAuth().getId().equals(id), owners);
        if (ow.isEmpty()) {
            owners.add(new Owner(name, birth, new Auth(id, pw)));
            return "[ " + name + " ]오너 환영합니다.";
        } else {
            return "이미 있는 아이디 입니다.";
        }
    }

    @GetMapping("/ownerLogin")
    public String ownerLogin(
        @RequestParam(name = "id") String id,
        @RequestParam(name = "pw") String pw
    ) {
        var ow = MemberController.findEl(e -> e.getAuth().getId().equals(id) && e.getAuth().getPw().equals(pw), owners);

        if(ow.isPresent()) return "[ " + ow.get().getName() + " ]오너 환영합니다.";
        else return "아이디 또는 비밀호가 다름니다.";
    }

    @GetMapping("/ownerFindId")
    public String ownerFindId(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth,
        @RequestParam(name = "id") String id
    ) {
        var owInfo = MemberController.findEl(u -> u.getName().equals(name) && u.getBirth().equals(birth), owners);
        var i = MemberController.findEl(e -> e.getAuth().getId().equals(id), owners);
        var p = owInfo.get().getAuth().getPw();

        if(owInfo.isPresent() && i.isPresent()) return name + "오너 의 pw 는 [ " + p + " ] 입니다.";
        else if(owInfo.isPresent() && !i.isPresent()) return "없는 id 입니다."; // 등록되지 않은 id 를 쓰면 에러
        else return "[ " + name + "]님은 오너가 아닙니다.";
    }

    @GetMapping("/findOwnerInfo")
    public String findOwnerInfo(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth
    ) {
        var ownerInfo = MemberController.findEl(u -> u.getName().equals(name) && u.getBirth().equals(birth), owners);
        
        var i = ownerInfo.get().getAuth().getId();
        var p = ownerInfo.get().getAuth().getPw();

        if(ownerInfo.isPresent()) {
             return "[ "+ name + " ]오너 의 id와 pw 는 [ " + i + " : " + p + " ] 입니다.";
        } else return "[ " + name + "]님은 오너가 아닙니다."; 
    }
}
