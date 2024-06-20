package heesu.me.springadvanceddemo.v2;

import heesu.me.springadvanceddemo.trace.TraceId;
import heesu.me.springadvanceddemo.trace.TraceStatus;
import heesu.me.springadvanceddemo.trace.hellotrace.HelloTraceV1;
import heesu.me.springadvanceddemo.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final HelloTraceV2 trace;

    public void save(String itemId, TraceId beforeTraceId){

        TraceStatus status = null;

        try {
            status = trace.beginSync(beforeTraceId, "OrderRepository.orderItem()");

            if(itemId.equals("ex")){
                throw new IllegalStateException("exception occurs.");
            }

            // save
            sleep(1000);

            trace.end(status);
        } catch (Exception e){
            trace.exception(status, e);
            throw e;
        }


    }

    private void sleep(int millis) {
        try{
            Thread.sleep(millis);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
