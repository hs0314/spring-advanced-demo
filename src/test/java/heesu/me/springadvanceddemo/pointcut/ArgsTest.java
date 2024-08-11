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
public class ArgsTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut getPointcut(String exp) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(exp);
        return pointcut;
    }

    /*
    execution(* *(Object))는 정적으로 클래스 선언 정보로 판단하기 떄문에 매칭 실패
    args(Object)는 동적으로 실제 넘어온 파라미터의 객체 인스턴스로 판단하기 때문에 매칭 성공
     */
    @Test
    @DisplayName("args를 사용해서 특정 타입(부모객체도 포함)을 매칭")
    void args() {

        Assertions.assertThat(this.getPointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        Assertions.assertThat(this.getPointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        Assertions.assertThat(this.getPointcut("args()").matches(helloMethod, MemberServiceImpl.class)).isFalse();
        Assertions.assertThat(this.getPointcut("args(..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        Assertions.assertThat(this.getPointcut("args(*)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        Assertions.assertThat(this.getPointcut("args(String, ..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
