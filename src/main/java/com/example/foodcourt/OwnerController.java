package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner/")
public class OwnerController {

    // 영업자(owner) - 메뉴, 가격 등록(수정, 삭제) - 결제확인, 결제취소 - 주문내역 list - 매출정보
    // 우리 음식점에 가장 인기가 좋은 메뉴 보여주기/

    @GetMapping("/SignUp")
    public String ownerSignUp(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth,
            @RequestParam(name = "brand") String brand, // 자기가 사용할 상표
            @RequestParam(name = "id") String id,
            @RequestParam(name = "pw") String pw
    ) {
        var ow = MemberController.findEl(m -> m. , Storage.getInstance().getOwners());
        if (ow.isEmpty()) {
            owners.add(new Owner(name, birth, brand, new Auth(id, pw), 0));
            return "[ " + name + " ]오너 환영합니다.";
        } else {
            return "이미 있는 아이디 입니다.";
        }
    }

    @GetMapping("/Login")
    public String ownerLogin(
        @RequestParam(name = "id") String id,
        @RequestParam(name = "pw") String pw
    ) {
        var ow = Storage.getInstance().getOwners().stream().filter(o -> o.getAuth().equals(new Auth(id, pw))).findFirst();

        if(ow.isPresent()) return "[ " + ow.get().getOwnerName() + " ]오너 환영합니다.";
        else return "아이디 또는 비밀호가 다름니다.";
    }

    @GetMapping("/Withdrawal")
    public String ownerWithdrawal( // 탈퇴
        @RequestParam(name = "id") String id,
        @RequestParam(name = "pw") String pw
    ) {
        var ow = MemberController.findEl(e -> e.getOwnerAuth().getId().equals(id) && e.getOwnerAuth().getPw().equals(pw), owners);
        var n = ow.get().getOwnerName();
        if (ow.isPresent()) {
            owners.remove(ow.get());
            return "[ " + n + " ]오너는 탈퇴하셨습니다.";
        } else {
            return "없는 정보입니다.";
        }
    }

    @GetMapping("/FindId")
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

    @GetMapping("/addFood") // 오너 브렌드에 음식 등록.
    public String addFood(
        @RequestParam(name="shopName") String shopName, // shop은 마스터가 추가해줌.
        @RequestParam(name="foodName") String foodName,
        @RequestParam(name="price") int price
    ) {
        var findShop = MemberController.findEl(s -> s.getShopName().equals(shopName) , RootController.shops);
        var findFood = MemberController.findEl(f -> f.getFoodName().equals(foodName), RootController.menus);

        if(findShop.isPresent() && !findFood.isPresent()) {
            menus.add(new Food(foodName, price));
            return "[ " + foodName + " ](이)가 추가되었습니다.";
        } else {
            return "이미 등록된 메뉴입니다.";
        }
    }

        // @GetMapping("/editShop")
    // public String editShop(
    //     @RequestParam(name = "toShopName") String toShopName,
    //     @RequestParam(name = "fromShopName") String fromShopName
    // ) {
    //     var sName = MemberController.findEl(s -> s.getShopName().equals(toShopName), shops);

    //     if(sName.isPresent()) {
    //         shops.remove(sName.get());
    //         shops.add(new Shop(fromShopName, menus));
    //         return "[ " + toShopName + " ]이(가) [ " + fromShopName + " ]으로 변경되었습니다.";
    //     } else return "없는 상표입니다.";
    // }
}

/*
주문서
주문내역
매출
 */