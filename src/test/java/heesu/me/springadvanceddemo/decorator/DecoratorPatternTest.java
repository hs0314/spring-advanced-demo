package heesu.me.springadvanceddemo.decorator;


import heesu.me.springadvanceddemo.decorator.code.*;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {

    @Test
    void noDecoratorTest(){
        Component realComponent = new RealComponent();
        Client client = new Client(realComponent);

        client.execute();
    }

    @Test
    void messageDecoratorTest(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Client client = new Client(messageDecorator);

        client.execute();
    }

    @Test
    void decoratorChainTest(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        Client client = new Client(timeDecorator);

        client.execute();
    }
}
