package heesu.me.springadvanceddemo.proxy.advisor;

import heesu.me.springadvanceddemo.proxy.OrderRepository;
import heesu.me.springadvanceddemo.proxy.OrderService;
import heesu.me.springadvanceddemo.proxy.OrderServiceImpl;
import heesu.me.springadvanceddemo.proxy.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class MultiAdvisorTest {

    @Test
    @DisplayName("N개의 프록시 생성")
    void advisorTest1() {
        // client -> proxy2(advisor2) -> proxy1(advisor1) -> target
        OrderService target = new OrderServiceImpl(new OrderRepository());

        //proxy1 생성
        ProxyFactory proxyFactory1 = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        proxyFactory1.addAdvisor(advisor1);
        OrderService proxy1 = (OrderService) proxyFactory1.getProxy();

        //proxy2 생성
        // proxy 적용순서에 따라서 target으로 proxy1을 셋팅해야함
        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory2.addAdvisor(advisor2);
        OrderService proxy2 = (OrderService) proxyFactory2.getProxy();

        proxy2.orderItem("item1");
    }

    @Test
    @DisplayName("하나의 프록시에 여러 advisor 생성")
    void advisorTest2() {
        // client -> proxy2(advisor2) -> proxy1(advisor1) -> target
        OrderService target = new OrderServiceImpl(new OrderRepository());

        //proxy1 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);

        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        // proxy factory에 N개의 advisor 추가
        proxyFactory.addAdvisor(advisor2);
        proxyFactory.addAdvisor(advisor1);
        OrderService proxy = (OrderService) proxyFactory.getProxy();

        proxy.orderItem("item1");
    }

    @Slf4j
    static class Advice1 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice1 call");
            return invocation.proceed();
        }
    }

    @Slf4j
    static class Advice2 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice2 call");
            return invocation.proceed();
        }
    }
}
