package heesu.me.springadvanceddemo.proxy.concrete_proxy;

import heesu.me.springadvanceddemo.proxy.OrderRepository;
import heesu.me.springadvanceddemo.proxy.OrderService;
import heesu.me.springadvanceddemo.proxy.OrderServiceImpl;
import heesu.me.springadvanceddemo.proxy.interface_proxy.OrderServiceInterfaceProxy;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 구체 클래스 기반 프록시
 */
//@Configuration
public class concreteProxyConfig {

    // LogTrace 스프링 컨테이너에서 빈 주입을 받고 클라이언트에서는 OrderRepository객체를 주입 받을때
    // 로직클래스를 상속받은 프록시객체(OrderRepositoryConcreteProxy)를 DI 받도록 한다
    //@Bean
    public OrderRepository orderRepository(LogTrace logTrace){

        OrderRepository orderRepository = new OrderRepository();
        return new OrderRepositoryConcreteProxy(orderRepository, logTrace);
    }
}
