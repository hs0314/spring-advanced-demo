package heesu.me.springadvanceddemo.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class LogTracePostProcessor implements BeanPostProcessor {
    private final Advisor advisor;

    public LogTracePostProcessor(Advisor advisor) {
        this.advisor = advisor;
    }

    // 특정 패키지에 대해서 프록시 객체로 변경해서 셋팅
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("beanName:{} bean:{}",beanName, bean.getClass());

        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith("heesu.me.springadvanceddemo.proxy")) {
            return bean;
        }

        // 프록시 적용 대상의 경우
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();

        log.info("create proxy. target:{} proxy:{}", bean.getClass(), proxy.getClass());

        return proxy;
    }
}
