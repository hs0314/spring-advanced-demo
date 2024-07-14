package heesu.me.springadvanceddemo.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component{

    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator called");
        long startTime = System.currentTimeMillis();

        String result = component.operation();

        long endTime = System.currentTimeMillis();

        String decoratedResult = "[" + (endTime - startTime) + "ms] " + component.operation();

        return decoratedResult;
    }
}
