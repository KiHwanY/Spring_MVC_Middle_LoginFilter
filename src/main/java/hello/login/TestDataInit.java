package hello.login;

import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */

    /*
    * @PostConstruct
    *
    *  -> @PostConstruct 는 의존성 주입이 이루어진 후 초기화를 수행하는 메서드이다.
    *     @PostConstruct 가 붙은 메서드는 클래스가 service(로직을 탈 때?로 생각 됨)를 수행하기 전에
    *     발생한다. 이 메서드는 다른 리소스에서 호출되지 않는다해도 수행된다.
    *
    * [사용하는 이유(장점)]
    *
    * 1.생성자(일반)가 호출 되었을 때, Bean은 아직 초기화 되지 않았다.
    * (예를 들어, 주입된 의존성이 없음)
    * 하지만, @PostConstruct 를 사용하면, Bean이 초기화 됨과 동시에 의존성을 확인할 수 있다.
    *  + 개인 의견으로 클래스 내의 @Autowired 를 붙여서 객체를 사용할 때, 생성자가 필요하다면
    * @PostConstruct 를 사용하면 될 것 같다. Bean이 등록되고 사용할 수 있으니까 말이다.
    *
    * 2. Bean lifecycle 에서 오직 한 번만 수행된다는 것을 보장할 수 있다.
    * (WAS가 올라가면서 Bean이 생성될 때 딱 한 번 초기화 함)
    * 그래서 @PostConstruct 를 사용하면 Bean이 여러번 초기화 되는 것을 방지할 수 있다.
    * */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test!");
        member.setName("테스터");

        memberRepository.save(member);

    }

}