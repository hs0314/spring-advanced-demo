package heesu.me.springadvanceddemo.proxy;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("exception occur!!");
        }

        sleep(1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
