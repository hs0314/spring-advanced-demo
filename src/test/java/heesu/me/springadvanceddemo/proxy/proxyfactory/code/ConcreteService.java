package heesu.me.springadvanceddemo.proxy.proxyfactory.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteService {

    public void call() {
        log.info("InterfaceServiceImpl call()");
    }
}
