package heesu.me.springadvanceddemo.proxy.interface_proxy;

import heesu.me.springadvanceddemo.proxy.OrderRepository;
import heesu.me.springadvanceddemo.proxy.OrderService;
import heesu.me.springadvanceddemo.proxy.OrderServiceImpl;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 인터페이스 기반 프록시
 */
@Configuration
public class InterfaceProxyConfig {

    // LogTrace, OrderRepository는 스프링 컨테이너에서 빈 주입을 받고, OrderServie에 대한 빈 주입은 프록시 인터페이스로 받도록 수기로 DI
    @Bean
    public OrderService orderService(LogTrace logTrace, OrderRepository orderRepository){
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository);
        return new OrderServiceInterfaceProxy(orderService, logTrace);
    }
}
