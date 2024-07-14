package heesu.me.springadvanceddemo.proxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    // 프록시도 실제 객체와 형태가 같아야하기 때문에 Subject 구현체
    // Client 입장에서는 Proxy객체도 Subject 구현체이기 때문에 변경이 발생하지 않음(OCP)
    @Override
    public String operation() {
        log.info("CacheProxy called");

        if(cacheValue == null) {
            cacheValue = target.operation();
        }

        return cacheValue;

    }
}
