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
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("within을 사용해서 하나의 명확한 타입을 매칭")
    void within() {
        // within은 표현식에 부모타입을 지정하면 매칭이 안되기 때문에 정확한 타입 명시를 해야함
        pointcut.setExpression("within(heesu.me.springadvanceddemo.member.MemberServiceImpl)");

        Assertions.assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
