package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/mapping/users") // 이렇게 resorce를 계층으로 식별하는 스타일을 많이 사용함
public class MappingClassController {
/*
회원 목록 조회: GET      /users
    회원 등록: POST     /users
    회원 조회: GET      /users/{userId}
    회원수정: PATCH /users/{userId}
    회원 삭제: DELETE /users/{userId}
*/

    @GetMapping
    public String user() {
        return "get users";
    }

    @PostMapping
    public String addUser() {
        return "post user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return "get userId=" + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update userId=" + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete userId=" +userId;
    }
}