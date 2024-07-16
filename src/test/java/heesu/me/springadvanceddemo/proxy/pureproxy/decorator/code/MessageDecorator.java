package heesu.me.springadvanceddemo.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component{

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator called");

        String operation = component.operation();
        String decoratedResult = "****" + operation + "****";

        return decoratedResult;
    }
}
