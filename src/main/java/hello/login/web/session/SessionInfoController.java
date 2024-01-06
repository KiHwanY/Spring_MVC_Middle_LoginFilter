package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }

        /*
        *  [asIterator() Method]
        * 이 열거에 포함 된 요소들을 순회 하는 Iterator를 return합니다.
        *
        * 컬렉션에 저장된 요소들을 읽어오는 방법을 표준화한 것.
        * 컬렉션에는 List,Set,Map 종류가 있다. 이걸 표준화 한 것이다.
        *
        * 즉, List에서 Set로 바꿨을 때 (=컬렉션 클래스를 다른 걸로 바꿨을 때),
        * 저장된 요소를 읽어오는 코드가 바꿔야 되는 것이다.
        *
        * 하지만, 그걸 iterator 로 읽어오면 컬렉션이 바꿔도 읽어오는 코드를 변경하지 않아도 된다.
        *
        * Enumeration 이랑 거의 같다.
        *
        *
        * forEachRemaining() 을 이용해서 Iterator 를 ArrayList 로 변환 할 수 있다.
        * */

        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

        log.info("sessionId={}", session.getId());
        log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());


        /*
        * sessionId : sessionId, JSESSIONID의 값이다. EX)34B14F008AA3527C9F8ED620EFD7A4E1
        * maxInactiveInterval : session의 유효 시간, ex) 1800sec , (30분)
        * creationTime : session create 일시
        * lastAccessedTime : session 과 연결된 user가 최근에 서버에 접근한 시간, 클라이언트에서 서버로 sessionId(JSESSIONID)를 요청한 경우에 갱신된다.
        * isNew : create session ? ed session ? 클라이언트에서 서버로 sessionId(JSESSIONID)를 요청해서 조회된 session ?
        * */

        return "세션 출력";

    }
}
