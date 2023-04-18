package com.example.foodcourt;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.function.Function;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// 음식점 list(음식점 검색) - 메뉴 list(메뉴 검색) - 음식 주문(결제, 주문 취소) - 주문내역 list

@RestController
public class OrderController {
    // shopList and foodList를 한번에 보여주는게 좋을 것 같다.
    @GetMapping("/login/shopList")
    public String shopList() {
        return RootController.shops.toString();
    }

    @GetMapping("/login/shop/foodList") // shop에 들어와서 메뉴보기
    public String foodList() {
        return OwnerController.menus.toString();
    }

    @GetMapping("/login/search")
    public String search(
        @RequestParam(name = "shopName", required = false) String shopName, 
        @RequestParam(name = "menu") String menu
    ) {
        // 메뉴를 검색하면 모든 식당에서 메뉴 검색
        // 특정 식당의 메뉴 검색
        var findShop = MemberController.findEl(s -> s.getShopName().equals(shopName) , RootController.shops); // 특정 가계
        var menu_ = filter(m -> m.getFoodName().equals(menu), OwnerController.menus); // 모든 가계

        if(findShop.isPresent()) {
            return findShop.get().getMenus().toString();
        } else {
            return menu_.toString();
        }

    }

    public static <T> ArrayList<T> filter(Function<T, Boolean> p, ArrayList<T> list) {
        if(list.isEmpty()) return new ArrayList<T>(); // 조건에 맞는 el를 담을 그릇
        else {
            T a = list.get(0);
            ArrayList<T> l = new ArrayList<>(list.subList(1, list.size()));
            
            ArrayList<T> result = filter(p, l);
            if(p.apply(a)) {
                result.add(0, a);
            } return result;
        }
    }

    @GetMapping("/login/shop/foodList/order") // 주문하기
    public String order(
        @RequestParam(name="name") String name,
        @RequestParam(name="addr") String addr,
        @RequestParam(name="orderMenu") String orderMenu,
        @RequestParam(name="pay") int pay // 지불금액
    ) {
        var info = MemberController.findEl(m -> m.getName().equals(name) && m.getAddr().equals(addr), MemberController.users);
        var haveMoney = info.get().getMoney() ; // 보유금액
        var shop_ = MemberController.findEl(s -> s.getg, null);

        if(info.isPresent() && haveMoney >= pay) {
            info.get().setMoney(pay); // 돈이 owner에게 가야함.

            return "ruf";
        }
    }
}

// 가계는 마스터가 추가해주는데 어느가계가 오너의 것인지 어떻게 판단하지?
