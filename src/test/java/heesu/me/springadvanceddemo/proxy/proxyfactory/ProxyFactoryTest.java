package heesu.me.springadvanceddemo.proxy.proxyfactory;

import heesu.me.springadvanceddemo.proxy.common.advice.TimeAdvice;
import heesu.me.springadvanceddemo.proxy.proxyfactory.code.ConcreteService;
import heesu.me.springadvanceddemo.proxy.proxyfactory.code.InterfaceService;
import heesu.me.springadvanceddemo.proxy.proxyfactory.code.InterfaceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK동적프록시 사용")
    void interfaceProxy(){
        InterfaceService target = new InterfaceServiceImpl();

        // 넘겨준 인스턴스의 정보를 기반으로 동적 프록시 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // proxyFactory가 사용할 부가정보 TiemAdvice는 Advice인터페이스 상속받은 MethodInterceptor를 구현한다
        proxyFactory.addAdvice(new TimeAdvice());
        //하기 설정은 무조건 CGLIB 사용해서 타겟 클래스 기반 프록시를 생성
        //proxyFactory.setProxyTargetClass(true);
        InterfaceService proxy = (InterfaceService) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

        // target의 인터페이스가 있으므로 jdk 동적프록시 기반 프록시생성
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("구체클래스만 있으면 CGLIB 사용")
    void concreteProxy(){
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

        // target의 구체클래스만 있으므로 CGLIB 기반 프록시생성
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

}
