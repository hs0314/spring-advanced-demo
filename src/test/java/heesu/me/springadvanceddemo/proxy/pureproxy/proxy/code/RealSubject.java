package heesu.me.springadvanceddemo.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealSubject implements Subject{
    @Override
    public String operation() {
        log.info("real subject called");
        this.sleep(1000);
        return "data";
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
