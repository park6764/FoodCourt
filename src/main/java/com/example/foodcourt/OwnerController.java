package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerController {

    // 영업자(owner) - 메뉴, 가격 등록(수정, 삭제) - 결제확인, 결제취소 - 주문내역 list - 매출정보
    final ArrayList<Owner> owners = new ArrayList<>();

    @GetMapping("/ownerSignUp")
    public String ownerSignUp(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth,
            @RequestParam(name = "id") String id,
            @RequestParam(name = "pw") String pw
    ) {
        var ow = MemberController.findEl(m -> m.getOwnerAuth().getId().equals(id), owners);
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
        var ow = MemberController.findEl(e -> e.getOwnerAuth().getId().equals(id) && e.getOwnerAuth().getPw().equals(pw), owners);

        if(ow.isPresent()) return "[ " + ow.get().getOwnerName() + " ]오너 환영합니다.";
        else return "아이디 또는 비밀호가 다름니다.";
    }

    @GetMapping("/ownerFindId")
    public String ownerFindId(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth,
        @RequestParam(name = "id") String id
    ) {
        var owInfo = MemberController.findEl(u -> u.getOwnerName().equals(name) && u.getOwnerBirth().equals(birth), owners);
        var i = MemberController.findEl(e -> e.getOwnerAuth().getId().equals(id), owners);
        var p = owInfo.get().getOwnerAuth().getPw();

        if(owInfo.isPresent() && i.isPresent()) return name + "오너 의 pw 는 [ " + p + " ] 입니다.";
        else if(owInfo.isPresent() && !i.isPresent()) return "없는 id 입니다."; // 등록되지 않은 id 를 쓰면 에러
        else return "[ " + name + "]님은 오너가 아닙니다.";
    }

    @GetMapping("/findOwnerInfo")
    public String findOwnerInfo(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth
    ) {
        var ownerInfo = MemberController.findEl(u -> u.getOwnerName().equals(name) && u.getOwnerBirth().equals(birth), owners);
        
        var i = ownerInfo.get().getOwnerAuth().getId();
        var p = ownerInfo.get().getOwnerAuth().getPw();

        if(ownerInfo.isPresent()) {
             return "[ "+ name + " ]오너 의 id와 pw 는 [ " + i + " : " + p + " ] 입니다.";
        } else return "[ " + name + "]님은 오너가 아닙니다."; 
    }
}
