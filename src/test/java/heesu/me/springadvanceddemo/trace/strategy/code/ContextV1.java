package heesu.me.springadvanceddemo.trace.strategy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV1 {

    private final Strategy strategy;

    public ContextV1(Strategy strategy){
        this.strategy = strategy;
    }

    public void execute(){
        strategy.call();
    }

}
