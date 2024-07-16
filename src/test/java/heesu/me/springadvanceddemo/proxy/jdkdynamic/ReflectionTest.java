package heesu.me.springadvanceddemo.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){
        Hello target = new Hello();

        // 두 로직은 호출 메서드만 다르고 코드 흐름이 같음
        // 공통로직1
        log.info("start");
        String result1 = target.callA();
        log.info("result={}",result1);

        // 공통로직2
        log.info("start");
        String result2 = target.callB();
        log.info("result={}",result2);
    }

    @Test
    void reflection1() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        // 클래스 정보
        Class classHello = Class.forName("heesu.me.springadvanceddemo.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        //callA의 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        this.dynamicMethodCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        this.dynamicMethodCall(methodCallB, target);
    }

    private void dynamicMethodCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        String result = (String) method.invoke(target);
        log.info("result={}",result);
    }

    static class Hello {
        public String callA(){
            log.info("call A");
            return "A";
        }

        public String callB(){
            log.info("call B");
            return "B";
        }
    }
}
