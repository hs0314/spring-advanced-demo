package heesu.me.springadvanceddemo.trace.threadlocal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldService {

    private String nameStore;

    public String logic(String name){
        log.info("save name:{} -> nameStore:{}", name, nameStore);
        nameStore = name;
        this.sleep(500);
        log.info("조회 nameStore:{}", nameStore);

        return nameStore;
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
