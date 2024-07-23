package heesu.me.springadvanceddemo.proxy.advisor;

import heesu.me.springadvanceddemo.proxy.OrderRepository;
import heesu.me.springadvanceddemo.proxy.OrderService;
import heesu.me.springadvanceddemo.proxy.OrderServiceImpl;
import heesu.me.springadvanceddemo.proxy.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
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
public class AdvisorTest {

    @Test
    @DisplayName("커스텀 advisor 생성")
    void advisorTest1() {
        OrderService target = new OrderServiceImpl(new OrderRepository());
        ProxyFactory proxyFactory = new ProxyFactory(target);

        //하나의 pointcut과 advice로 구성
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

        proxyFactory.addAdvisor(advisor);

        OrderService proxy = (OrderService) proxyFactory.getProxy();

        proxy.orderItem("item1");

    }

    @Test
    @DisplayName("커스텀 advisor 생성(커스텀 포인트컷 생성)")
    void advisorTest2() {
        OrderService target = new OrderServiceImpl(new OrderRepository());
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 커스텀 포인트컷 추가
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new Pointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return ClassFilter.TRUE;
            }

            @Override
            public MethodMatcher getMethodMatcher() {
                return new MethodMatcher(){
                    @Override
                    public boolean matches(Method method, Class<?> targetClass){
                        boolean result = method.getName().equals("orderItem");
                        log.info("pointcut call method:{}, targetClass:{}", method.getName(), targetClass);
                        log.info("pointcut result:{}",result);
                        return result;
                    }

                    @Override
                    public boolean isRuntime() {
                        return false;
                    }

                    @Override
                    public boolean matches(Method method, Class<?> targetClass, Object... args) {
                        return false;
                    }
                };
            }
        }, new TimeAdvice());

        proxyFactory.addAdvisor(advisor);

        OrderService proxy = (OrderService) proxyFactory.getProxy();

        proxy.orderItem("item1");
    }

    @Test
    @DisplayName("커스텀 advisor 생성(스프링 제공 포인트컷 사용)")
    void advisorTest3() {
        OrderService target = new OrderServiceImpl(new OrderRepository());
        ProxyFactory proxyFactory = new ProxyFactory(target);

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        // 포인트컷 조건 만족을 안하기 때문에 어드바이스(부가처리) 적용 X
        pointcut.setMappedName("orderItemXX");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());

        proxyFactory.addAdvisor(advisor);

        OrderService proxy = (OrderService) proxyFactory.getProxy();

        proxy.orderItem("item1");

    }
}
