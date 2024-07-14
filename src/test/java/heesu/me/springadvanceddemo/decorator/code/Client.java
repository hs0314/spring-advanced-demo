package heesu.me.springadvanceddemo.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client{

    private Component component;

    public Client(Component component) {
        this.component = component;
    }

    public void execute() {
        String result = component.operation();
        log.info("result={}", result);
    }
}
