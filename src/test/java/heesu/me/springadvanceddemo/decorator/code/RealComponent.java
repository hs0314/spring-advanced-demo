package heesu.me.springadvanceddemo.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealComponent implements Component{
    @Override
    public String operation() {
        log.info("RealComponent called");
        return "data";
    }
}
