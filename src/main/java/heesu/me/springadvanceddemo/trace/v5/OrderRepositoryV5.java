package heesu.me.springadvanceddemo.trace.v5;

import heesu.me.springadvanceddemo.trace.callback.TraceTemplate;
import heesu.me.springadvanceddemo.trace.logtrace.LogTrace;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV5 {

    private final TraceTemplate template;

    public OrderRepositoryV5(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId){

        template.execute(
                "OrderRepository.orderItem()",
                () -> {
                    if(itemId.equals("ex")){
                        throw new IllegalStateException("exception occurs.");
                    }

                    // save
                    sleep(1000);

                    return null;
                }
        );
    }

    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
