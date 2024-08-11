package heesu.me.springadvanceddemo.pointcut;

import heesu.me.springadvanceddemo.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {

        // helloMethod=public java.lang.String heesu.me.springadvanceddemo.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    @DisplayName("가장 정확하게 조건을 명시한 포인트컷")
    void exactMatch() {
        // execution(접근제어자 반환타입 선언타입 메서드명 파라미터 예외)
        // 접근제어자, 선언타입, 예외는 생략 가능
        pointcut.setExpression("execution(public String heesu.me.springadvanceddemo.member.MemberServiceImpl.hello(String))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("가장 생략을 많이한 포인트컷")
    void allMatch() {
        // 반환타입:* / 메서드명:* / 파라미터: (..)
        pointcut.setExpression("execution(* *(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("메서드명 매칭 포인트컷")
    void nameMatch() {
        // 메서드명 앞뒤에 *로 대체가 가능
        pointcut.setExpression("execution(* *el*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭 포인트컷1 - 정확한 패키지 매칭")
    void packageExactMatching1() {
        pointcut.setExpression("execution(* heesu.me.springadvanceddemo.member.*.*(String))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지 매칭 포인트컷2 - 명시된 패키지의 서브패키지까지 매칭")
    void packageExactMatching2() {
        // 패키지에 ..으로 붙이면 하위 패키지를 포함한다는 의미
        pointcut.setExpression("execution(* heesu.me.springadvanceddemo.member..*.*(String))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타입 매칭 포인트컷")
    void typeExactMatching1() {
        pointcut.setExpression("execution(* heesu.me.springadvanceddemo.member.MemberServiceImpl.*(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("부모 타입 매칭 포인트컷")
    void typeExactMatching2() throws NoSuchMethodException {
        // MemberService인터페이스(부모타입)로 타입 매칭을 해도 자식 타입(Impl서비스) 매칭 가능
        // 그러나 최종적으로 부모타입에 있는 메서드만 매칭이 가능
        pointcut.setExpression("execution(* heesu.me.springadvanceddemo.member.MemberService.*(..))");

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();

         // 자식타입에만 있는 메서드는 매칭 불가
        Assertions.assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("String 파라미터 매칭 포인트컷")
    void argsMatching1() {
        // String 타입만 매칭
        pointcut.setExpression("execution(* *(String))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("모든 타입 + 1개 파라미터 매칭 포인트컷")
    void argsMatching2() {
        // String 타입만 매칭
        pointcut.setExpression("execution(* *(*))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("모든 타입 + N개 파라미터 매칭 포인트컷")
    void argsMatching3() {
        // String 타입만 매칭
        pointcut.setExpression("execution(* *(..))");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
