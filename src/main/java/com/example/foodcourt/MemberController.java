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
        var info = findEl(e -> e.getAuth().getId().equals(id), users);
        if (info.isEmpty()) {
            users.add(new Member(name, birth, addr, new Auth(id, pw), 0, Order));
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

    @GetMapping("/login/Withdrawal")
    public String memberWithdrawal(
        @RequestParam(name = "id") String id,
        @RequestParam(name = "pw") String pw
    ) {
        var info = findEl(e -> e.getAuth().getId().equals(id), users);
        var n = info.get().getName();
        if (info.isPresent()) {
            users.remove(info.get());
            return "[ " + n + " ]님은 탈퇴하셨습니다.";
        } else {
            return "없는 정보입니다.";
        }
    }

    public static <T> Optional<T> findEl(Function<T, Boolean> p, ArrayList<T> list) {
        if (list.isEmpty())
            return Optional.empty();
        else {
            final ArrayList<T> ls = new ArrayList<>(list);
            var a = ls.get(0);
            ArrayList<T> l = (ArrayList<T>)ls.subList(1, ls.size());
            if (p.apply(a))
                return Optional.of(a);
            else{
                var l_ = findEl(p, l);
                return l_;
            }
        }
    }

    // static <T> Optional<T> _findEl(Function<T, Boolean> p, ArrayList<T> list) {
    //     Optional<T> x = Optional.empty();

    //     for (var a : list) {
    //         if(p.apply(a)) { x = Optional.of(a); break; }
    //         else continue;
    //     }
    //     return x;
    // }

    // static <T> Optional<T> findElem(Function<T, Boolean> p, ArrayList<T> list) {
    //     return head(filter(p, list));
    // }

    // static <T> Optional<T> head(ArrayList<T> ls) {
    //     if (ls.isEmpty()) {
    //         return Optional.empty();
    //     } else {
    //         return Optional.of(ls.get(0));
    //     }
    // }

    // public static <T> ArrayList<T> filter(Function<T, Boolean> p, ArrayList<T> list) {
    //     if(list.isEmpty()) return new ArrayList<T>();
    //     else {
    //         var n = list.get(0);
    //         var l = new ArrayList<>(list.subList(1, list.size()));
            
    //         var result = filter(p, l);
    //         if(p.apply(n)) {
    //             result.add(0, n);
    //         } return result;
    //     }
    // }
}

/*


회원(3) - 회원가입 - 로그인(id 찾기, pw찾기(비번변경), 회원탈퇴) 

 */
