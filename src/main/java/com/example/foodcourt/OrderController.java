package com.example.foodcourt;

import java.lang.module.FindException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;


// member가 로그인 후 주문
// 음식점 list 와 메뉴 list 를 볼 수 있고, 음식점 또는 메뉴 검색 가능.

@RestController
@RequestMapping("/login/")
public class OrderController {

    // shopList and foodList를 한번에 보여주는게 좋을 것 같다.
    @GetMapping("/shopList")
    public ResponseEntity<ArrayList<Shop>> shopList() {
        if(Storage.getInstance().getLoggedInMember().isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 인증 안됨.
        else return ResponseEntity.ok(new ArrayList<>(Storage.getInstance().getOwners().stream()
                                     .map(o -> o.getShop()).toList()));
    }

    @GetMapping("/shop/foodList") // shop에 들어와서 메뉴보기
    public Optional<ArrayList<Food>> foodList(@RequestParam String shopName) {
        return Storage.getInstance().getOwners().stream()
            .map(o -> o.getShop())
            .filter(s -> s.getShopName().equals(shopName))
            .findFirst()
            .map(s -> s.getMenus());
    }

    @PostMapping("/shop/foodList/order") // 주문하기
    @ResponseStatus(HttpStatus.OK)
    public void order(
        @RequestBody Order order,
        @RequestParam(name = "pay") int pay // 지불금액
    ) {
        order.setDate(LocalDate.now());
        var member = Storage.getInstance().getLoggedInMember();
        
        if(member.isPresent()) {
            if(member.get().getMoney() >= pay) {
                member.get().getOrders().add(order);
                final var shop = order.getShop();
                final var ow = Storage.getInstance().getOwners().stream().filter(o -> o.getShop().equals(shop)).findFirst();

                member.get().pay(pay);
                ow.ifPresent(o -> { o.getOrders().add(order); o.addProfit(pay); });
            }
        }
    }
}

// 가계는 마스터가 추가해주는데 어느가계가 오너의 것인지 어떻게 판단하지?