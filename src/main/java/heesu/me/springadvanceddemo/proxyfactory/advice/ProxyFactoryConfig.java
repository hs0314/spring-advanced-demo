package heesu.me.springadvanceddemo.proxyfactory.advice;

import heesu.me.springadvanceddemo.proxy.OrderRepository;
import heesu.me.springadvanceddemo.proxy.OrderService;
import heesu.me.springadvanceddemo.proxy.OrderServiceImpl;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// orderService 빈 등록이 겹치므로 필요에 따라서 하나의 config만 활용
//@Configuration
public class ProxyFactoryConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    //@Bean
    public OrderService orderService(LogTrace logTrace, OrderRepository orderRepository){
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository);

        ProxyFactory factory = new ProxyFactory(orderService);
        factory.addAdvisor(this.getAdvisor(logTrace));

        return (OrderService) factory.getProxy();
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames(PATTERNS);

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
