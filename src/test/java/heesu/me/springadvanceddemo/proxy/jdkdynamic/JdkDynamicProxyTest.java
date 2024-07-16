package heesu.me.springadvanceddemo.proxy.jdkdynamic;

import heesu.me.springadvanceddemo.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicProxyTest(){
        AInterface Atarget = new AImpl();
        BInterface Btarget = new BImpl();

        TimeInvocationHandler Ahandler = new TimeInvocationHandler(Atarget);
        TimeInvocationHandler Bhandler = new TimeInvocationHandler(Btarget);

        // 동적 프록시 생성
        AInterface Aproxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader(), // 프록시 클래스 로드용 클래스로더
                new Class[]{AInterface.class}, // AInterface 인터페이스 구현한 프록시 생성
                Ahandler // 프록시에서 호출되는 메서드
        );

        BInterface Bproxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader(), // 프록시 클래스 로드용 클래스로더
                new Class[]{BInterface.class}, // BInterface 인터페이스 구현한 프록시 생성
                Bhandler // 프록시에서 호출되는 메서드
        );

        Aproxy.call();
        Bproxy.call();

        // 동적 생성된 프록시객체
        log.info("A proxyClass={}", Aproxy.getClass());
        log.info("B proxyClass={}", Bproxy.getClass());
    }
}
