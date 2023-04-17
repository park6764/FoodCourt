package com.example.foodcourt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    final ArrayList<MemberInfo> users = new ArrayList<>();
    final ArrayList<Food> orderFoodList = new ArrayList<>();
    

    private final String rootId = "root";
    private final String rootPw = "1234";

    @GetMapping("/")
    public String main() {
        return "<푸드코드_test>";
    }

    @GetMapping("/signUp")
    public String signUp(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth,
            @RequestParam(name = "id") String id,
            @RequestParam(name = "pw") String pw
    ) {
        var info = findEl(m -> m.getAuth().getId().equals(id), users);
        if (info.isEmpty()) {
            users.add(new MemberInfo(name, birth, new Auth(id, pw), orderFoodList));
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
        var u = findEl(e -> e.getAuth().getId().equals(id) && e.getAuth().getPw().equals(pw), users);

        if(u.isPresent()) return "[ " + u.get().getName() + " ]님 환영합니다.";
        else return "아이디 또는 비밀호가 다름니다.";
    }

    @GetMapping("/root") 
    public String root(
        @RequestParam(name = "id") String id,
        @RequestParam(name = "pw") String pw
    ) {
        if(id.equals(rootId) && pw.equals(rootPw)) return "[ 마스터 ]님 환영합니다.";
        else return "아이디 또는 비밀호가 다름니다.";
    }

    @GetMapping("/findId")
    public String findId(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth,
        @RequestParam(name = "id") String id
    ) {
        var userInfo = findEl(u -> u.getName().equals(name) && u.getBirth().equals(birth), users);
        var i = findEl(e -> e.getAuth().getId().equals(id), users);
        var p = userInfo.get().getAuth().getPw();

        if(userInfo.isPresent() && i.isPresent()) return name + "님 의 pw 는 [ " + p + " ] 입니다.";
        else if(userInfo.isPresent() && !i.isPresent()) return "없는 id 입니다."; // 등록되지 않은 id 를 쓰면 에러
        else return "[ " + name + "]님은 회원이 아닙니다.";
    }

    @GetMapping("/findMemberInfo")
    public String findMemberInfo(
        @RequestParam(name = "name") String name,
        @RequestParam(name = "birth") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate birth
    ) {
        var userInfo = findEl(u -> u.getName().equals(name) && u.getBirth().equals(birth), users);
        
        var i = userInfo.get().getAuth().getId();
        var p = userInfo.get().getAuth().getPw();

        if(userInfo.isPresent()) {
             return "[ "+ name + " ]님 의 id와 pw 는 [ " + i + " : " + p + " ] 입니다.";
        } else return "[ " + name + "]님은 회원이 아닙니다."; 
    }

    public static <T> Optional<T> findEl(Function<T, Boolean> p, ArrayList<T> ls) {
        if (ls.isEmpty())
            return Optional.empty();
        else {
            final var l = (ArrayList<T>) ls.clone();
            var a = l.get(0);
            if (p.apply(a))
                return Optional.of(a);
            else
                return findEl(p, l);
        }
    }
}

/*


회원(3) - 회원가입 - 로그인(id 찾기, pw찾기(비번변경), 회원탈퇴) - 음식점 list(음식점 검색) - 메뉴 list(메뉴 검색) - 음식 주문(결제, 주문 취소) - 주문내역 list

 */
