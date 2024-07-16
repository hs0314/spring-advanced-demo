package heesu.me.springadvanceddemo.proxy.pureproxy.proxy;


import heesu.me.springadvanceddemo.proxy.pureproxy.proxy.code.CacheProxy;
import heesu.me.springadvanceddemo.proxy.pureproxy.proxy.code.Client;
import heesu.me.springadvanceddemo.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        Client client = new Client(realSubject);

        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest(){
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        Client client = new Client(cacheProxy);

        client.execute();
        client.execute();
        client.execute();
    }
}
