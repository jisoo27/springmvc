package hello.springmvc.basic.request;


import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}", username);
        log.info("age = {}", age);

        response.getWriter().write("ok"); // return 타입이 void고 response에 값을 써버리면 값을 볼 수 있다.
    }

    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력
     * */

    @ResponseBody // View 조회를 무시하고 , Http message body 에 직접 해당 내용 입력
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age = {}", memberName, memberAge);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능
     * */

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age = {}", username, age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
     * */

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age = {}", username, age);
        return "ok";
    }

    /**
     * @RequestParam.required
     * /request-param -> username이 없으므로 예외 *
        - 기본값이 파라미터 필수(true)이다.
     * 주의!
     * /request-param?username= -> 빈문자로 통과 *

     * 주의!
     * /request-param
     * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer 변경해야 함(또는 다음에 나오는
    defaultValue 사용)
     */

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {

        log.info("username={}, age = {}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValue 사용 *
     * 파라미터에 값이 없는 경우 defaultValue를 사용하면 기본 값을 적용할 수 있지만
     * 이미 기본 값이 있기 때문에 required는 의미가 없다
     *
     * 참고: defaultValue는 빈 문자의 경우에도 적용
     *  /request-param?username=
     */

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") Integer age) {

        log.info("username={}, age = {}", username, age);
        return "ok";
    }

    /**
     * @RequestParam Map, MultiValueMap
     * 파라미터 값이 1개가 확실하다면 Map을 사용해도되지만, 그렇지 않다면 MultiValueMap을 사용하자
     * @RequestParam Map
     *   Map(key=value)
     * @RequestParam MutiValueMap
     *   MultiValueMap(key=[value1, value2, ...] ex) (key=userIds, value=[id1, id2])
     */

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, String> paramMap) {
        log.info("username={}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /*
    * @ModelAttribute 사용
    * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때 자세히 설명
    * 마치 마법처럼 HelloData 객체가 생성되고, 요청 파라미터의 값도 모두 들어가 있다.
    * 스프링MVC는 @ModelAttribute 가 있으면 다음을 실행한다. HelloData 객체를 생성한다.
    * 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다. / 객체의 프로퍼티란 getter, setter를 의미
    *  그리고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩) 한다. / 바인딩 : 데이터를 넣는것
    * 예) 파라미터 이름이 username 이면 setUsername() 메서드를 찾아서 호출하면서 값을 입력한다.
    *
    * */

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age = {}",helloData.getUsername(), helloData.getAge());
        return "ok";
    }

}
