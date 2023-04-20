package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner/")
public class OwnerController {

    // 영업자(owner) - 메뉴, 가격 등록(수정, 삭제) - 결제확인, 결제취소 - 주문내역 list - 매출정보
    // 우리 음식점에 가장 인기가 좋은 메뉴 보여주기/

    @PostMapping("/SignUp")
    public String ownerSignUp(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth,
            @RequestBody Shop shop, // 자기가 사용할 상표
            @RequestParam(name = "id") String id,
            @RequestParam(name = "pw") String pw
    ) {
        var ow = Storage.getInstance().getOwners().stream().filter(o -> o.getAuth().equals(new Auth(id, pw))).findFirst();
        if (ow.isEmpty()) {
            Storage.getInstance().getOwners().add(new Owner(name, birth, shop, new Auth(id, pw), 0, new ArrayList<>()));
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

        if(ow.isPresent()) {
            Storage.getInstance().setLoggedInOwner(ow);
            return "[ " + ow.get().getName() + " ]오너 환영합니다.";
        }
        else return "아이디 또는 비밀호가 다름니다.";
    }

    @GetMapping("/Withdrawal")
    public String ownerWithdrawal() {
        var ow = Storage.getInstance().getLoggedInOwner();
        
        if (ow.isPresent()) {
            var n = ow.get().getName();
            Storage.getInstance().getOwners().remove(ow.get());
            return "[ " + n + " ]오너는 탈퇴하셨습니다.";
        } else {
            return "먼저 로그인하세요.";
        }
    }

    @GetMapping("/FindAuth")
    public ResponseEntity<Auth> ownerFindAuth(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth
    ) {
        var owInfo = Storage.getInstance().getOwners().stream().filter(o -> o.getName().equals(name) && o.getBirth().equals(birth)).findFirst();
        
        if(owInfo.isPresent()) {
            var i = owInfo.get().getAuth().getId();
            var p = owInfo.get().getAuth().getPw();
            return ResponseEntity.ok(new Auth(i, p));
        }
        else return ResponseEntity.notFound().build();
    }

    @PostMapping("/addFood") // 오너 브렌드에 음식 등록.
    public String addFood(
        @RequestBody Food food
    ) {
        var shop = Storage.getInstance().getLoggedInOwner().map(o -> o.getShop());

        if(shop.isPresent()) {
            Storage.getInstance().getLoggedInOwner().get().getShop().getMenus().add(food);
            return "[ " + food + " ](이)가 추가되었습니다.";
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