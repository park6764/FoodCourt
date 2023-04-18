package com.example.foodcourt;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    // 관리자(root) - 음식점 등록(수정, 삭제) - 영업자 정보 - 회원 정보
    final static ArrayList<Shop> shops = new ArrayList<>();
    final static ArrayList<Food> menus = new ArrayList<>();

    @GetMapping("/root/shopList")
    public String shopList() {
        return shops.toString();
    }

    @GetMapping("/root/userInfo")
    public String userInfo(ArrayList<MemberInfo> users) {
        return users.toString();
    }

    @GetMapping("/root/OwnerInfo")
    public String OwnerInfo(ArrayList<Owner> owners) {
        return owners.toString();
    }

    @GetMapping("/root/shopList")
    public String OwnerInfo() {
        return shops.toString();
    }
    
    @GetMapping("/root/addShop")
    public String addShop( // 오너가 마스터한테 요청하여 브렌드를 등록한다고 가정.
        @RequestParam(name = "shopName") String shopName
    ) {
        var sName = MemberController.findEl(s -> s.getShopName().equals(shopName), shops);

        if(sName.isPresent()) return "이미 등록된 상표입니다.";
        else {
            shops.add(new Shop(shopName, menus));
            return "[ " + shopName + " ]이(가) 등록되었습니다.";
        }
    }

    @GetMapping("/root/delShop")
    public String delShop(
        @RequestParam(name = "shopName") String shopName
      ) {
        var sName = MemberController.findEl(s -> s.getShopName().equals(shopName), shops);

        if(sName.isPresent()) {
            shops.remove(sName.get());
            return "[ " + shopName + " ]이(가) 삭제되었습니다.";
        } else return "없는 상표입니다.";
    }

    @GetMapping("/root/editShop")
    public String editShop(
        @RequestParam(name = "toShopName") String toShopName,
        @RequestParam(name = "fromShopName") String fromShopName
    ) {
        var sName = MemberController.findEl(s -> s.getShopName().equals(toShopName), shops);

        if(sName.isPresent()) {
            shops.remove(sName.get());
            shops.add(new Shop(fromShopName, menus));
            return "[ " + toShopName + " ]이(가) [ " + fromShopName + " ]으로 변경되었습니다.";
        } else return "없는 상표입니다.";
    }

    
}
