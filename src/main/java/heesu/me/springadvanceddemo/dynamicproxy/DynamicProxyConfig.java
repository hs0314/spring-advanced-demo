package heesu.me.springadvanceddemo.dynamicproxy;

import heesu.me.springadvanceddemo.proxy.OrderRepository;
import heesu.me.springadvanceddemo.proxy.OrderService;
import heesu.me.springadvanceddemo.proxy.OrderServiceImpl;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

// ProxyFactoryConfig에서 orderService 빈 등록이 겹치므로 필요에 따라서 하나의 config만 활용
// @Configuration
public class DynamicProxyConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    // JDK 동적 프록시를 활요하는 방법은 인터페이스가 필수적이고 구체클래스만 있는 경우에는 CGLIB을 활용해야한다
    //@Bean
    public OrderService orderService(LogTrace logTrace, OrderRepository orderRepository){
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository);

        OrderService proxy = (OrderService) Proxy.newProxyInstance(
                OrderService.class.getClassLoader(),
                new Class[]{OrderService.class},
                new LogTraceFilterHandler(orderService, logTrace, PATTERNS)
        );

        return proxy;
    }

    // 메서드명 필터 없는 basic 버전
    /*
    @Bean
    public OrderService orderService(LogTrace logTrace, OrderRepository orderRepository){
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository);

        OrderService proxy = (OrderService) Proxy.newProxyInstance(
                OrderService.class.getClassLoader(),
                new Class[]{OrderService.class},
                new LogTraceBasicHandler(orderService, logTrace)
        );

        return proxy;
    }
     */
}
