package heesu.me.springadvanceddemo.pointcut;

import heesu.me.springadvanceddemo.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Slf4j
@SpringJUnitConfig(TargetWithinTest.Config.class)
@EnableAspectJAutoProxy
public class TargetWithinTest {

    @Autowired
    Child child;

    @Test
    void success() {
        log.info("child proxy={}", child.getClass());

        //@target은 인스턴스 기준으로 판단 => 부모, 자식메서드가 모두 aop적용
        //@withn은 지정된 메서드에만 aop적용 (@ClassAop가 붙은 자식클래스 메서드만 aop적용)

        child.childMethod(); // 부모, 자식 모두 있는 메서드
        child.parentMethod(); // 부모만 있는 메서드
    }

    // 동적으로 테스트에 필요한 부모, 자식 클래스, aspect객체를 생성
    static class Config {
        @Bean
        public Parent parent(){
            return new Parent();
        }
        @Bean
        public Child child(){
            return new Child();
        }
        @Bean
        public TargetWithinAspect TargetWithinAspect(){
            return new TargetWithinAspect();
        }
    }

    static class Parent {
        public void parentMethod(){}
    }

    @ClassAop
    static class Child extends Parent {
        public void childMethod(){}
    }

    @Slf4j
    @Aspect
    static class TargetWithinAspect {

        //@target : 인스턴스 기준 모든 메서드의 조인 포인트 설정, 부모 타입 메서드도 적용
        // target은 runtime시점에만 체크할 수 있는 조건이기 때문에 단독으로 사용 시, 컨테이너는 모든 스프링빈에 대해서 AOP를 적용하려고 하고
        // 그렇게 되면 스프링 내부적으로 final로 등록하는 빈들의 경우 오류가 발생
        // ** 사용 시, execution을 통해서 최대한 적용범위를 줄여줘야함
        @Around("execution(* heesu.me.springadvanceddemo..*(..)) && @target(heesu.me.springadvanceddemo.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //@within : 선택된 클래스 내부에 있는 메서드만 조인 포인트 설정, 부모타입 메서드 미적용
        @Around("execution(* heesu.me.springadvanceddemo..*(..)) && @within(heesu.me.springadvanceddemo.member.annotation.ClassAop)")
        public Object atWitnin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
